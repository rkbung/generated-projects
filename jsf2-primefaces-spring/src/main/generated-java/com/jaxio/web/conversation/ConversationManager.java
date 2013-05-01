/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/conversation/ConversationManager.p.vm.java
 */
package com.jaxio.web.conversation;

import static com.google.common.collect.Maps.newTreeMap;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static com.jaxio.web.conversation.ConversationHolder.setCurrentConversation;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.omnifaces.util.Faces;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The conversation manager is responsible for creating conversations, managing their lifecycle and calling the conversation listeners.
 */
@Named
@Singleton
public class ConversationManager implements ApplicationContextAware {
    private static final String CONVERSATION_MAP = "conversationMap";
    private static ConversationManager instance;
    private static final Logger log = LoggerFactory.getLogger(ConversationManager.class);

    private ApplicationContext applicationContext;
    private Collection<ConversationListener> conversationListeners;
    private int maxConversations = 5;

    /**
     * This method should be used only in the following cases: 1) from code having no spring awareness, like filters. 2) from code that are session scoped in
     * order to avoid serialization of the service. In other cases, please have the conversationManager injected normally.
     */
    public static ConversationManager getInstance() {
        return instance;
    }

    public ConversationManager() {
        if (instance == null) {
            instance = this;
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * The maximum number of conversations a given user can open simultaneously.
     */
    public int getMaxConversations() {
        return maxConversations;
    }

    /**
     * Whether the max number of conversations per user is reached. Used in createConversation (which has no FacesContext yet).
     */
    public boolean isMaxConversationsReached(HttpSession session) {
        return conversationMap(session).size() >= maxConversations;
    }

    /**
     * Returns the current conversation. Note that this method is mainly here so it can be used from the view.
     * Use directly ConversationHolder.getCurrentConversation() from Java code.
     */
    public Conversation getCurrentConversation() {
        return ConversationHolder.getCurrentConversation();
    }

    // --------------------------------------
    // Manage conversation lifecycle
    // --------------------------------------

    public Conversation beginConversation(ConversationContext<?> ctx) {
        HttpSession session = Faces.getSession();
        Map<String, Conversation> conversationMap = conversationMap(session);

        handleMaxConversationsReached(session, conversationMap);

        Conversation conversation = Conversation.newConversation(session, ctx);
        conversationMap.put(conversation.getId(), conversation);
        conversationCreated(conversation);
        conversation.pushNextContextIfNeeded();
        return conversation;
    }

    private void handleMaxConversationsReached(HttpSession session, Map<String, Conversation> conversationMap) {
        if (isMaxConversationsReached(session)) {
            // FIFO conversation eviction
            String keyToEvict = conversationMap.keySet().iterator().next();
            log.info("Max number of conversations ({}) reached. Evicting conversation {} using fifo policy", maxConversations, keyToEvict);
            conversationMap.remove(keyToEvict); // TODO: special treatment for evicted conversation?
        }
    }

    /**
     * Resume the {@link Conversation} having the passed id. Before resuming it, if a pending ConversationContext is present, 
     * it is pushed on the conversation contextes stack. 
     * @param id the id of the conversation to resume 
     * @param ccid the id of the conversation context that should be on top of the stack. 
     * @param request
     * @throws UnexpectedConversationException
     */
    public void resumeConversation(String id, String ccid, HttpServletRequest request) throws UnexpectedConversationException {
        Conversation conversation = conversationMap(request.getSession()).get(id);

        if (conversation != null) {
            conversation.pushNextContextIfNeeded();

            // compare the context id
            if (!conversation.getCurrentContext().getId().equals(ccid)) {
                conversation.handleOutOfSynchContext(ccid, request);
            }
            conversationResuming(conversation, request);
            setCurrentConversation(conversation);
        } else {
            throw new UnexpectedConversationException("No conversation found for id=" + id, request.getRequestURI(), "/home.faces");
        }
    }

    /**
     * Pause the current conversation. Before pausing it, pops the current context as needed.
     * In case all contextes are popped, then conversation is ended.
     */
    public void pauseCurrentConversation(HttpServletRequest request) {
        Conversation conversation = getCurrentConversation();

        // we check for not null because the conversation could have 
        // been ended during the current request.
        if (conversation != null) {
            // call order of 2 methods below is important as we want all the contextes (even the one we are about to be popped)
            // to be visible from the conversation listener.
            conversationPausing(conversation);
            conversation.popContextesIfNeeded();

            if (conversation.getConversationContextesCount() == 0) {
                // all was popped, we consider that this is the natural end of the conversation.
                endCurrentConversation(request.getSession());
            } else {
                setCurrentConversation(null);
            }
        }
    }

    /**
     * End the current Conversation. Invoked from place where no FacesContext is present.
     */
    public void endCurrentConversation(HttpSession session) {
        Conversation endedConversation = endCurrentConversationCommon();
        conversationMap(session).remove(endedConversation.getId());
    }

    /**
     * End the current Conversation. Requires a FacesContext to be present.
     */
    public void endCurrentConversation() {
        Conversation endedConversation = endCurrentConversationCommon();
        conversationMap().remove(endedConversation.getId());
    }

    private Conversation endCurrentConversationCommon() {
        Conversation conversation = getCurrentConversation();
        log.info("Ending conversation {}", conversation.getId());
        conversationEnding(conversation);
        setCurrentConversation(null);
        return conversation;
    }

    // --------------------------------------------
    // Used from view to display conversations menu
    // --------------------------------------------

    /**
     * Returns the number of conversations for the current user. Used in the conversation menu.
     */
    public int getConversationCount() {
        return conversationMap().size();
    }

    public MenuModel getConversationMenuModel() {
        MenuModel model = new DefaultMenuModel();
        Conversation currentConversation = getCurrentConversation();
        for (Conversation conversation : conversationMap().values()) {
            MenuItem htmlMenuItem = new MenuItem();
            htmlMenuItem.setValue(conversation.getLabel());
            htmlMenuItem.setUrl(conversation.getUrl());
            if (currentConversation != null && currentConversation.getId().equals(conversation.getId())) {
                htmlMenuItem.setDisabled(true);
            }
            if (isNotBlank(conversation.getLabel())) {
                model.addMenuItem(htmlMenuItem);
            }
        }
        return model;
    }

    public boolean getRenderBreadCrumb() {
        return getCurrentConversation().getConversationContextes().size() > 1;
    }

    public MenuModel getBreadCrumbMenuModel() {
        MenuModel model = new DefaultMenuModel();
        Conversation currentConversation = getCurrentConversation();
        Stack<ConversationContext<?>> ctxStack = currentConversation.getConversationContextes();
        int beforeLastIndex = ctxStack.size() - 2;
        Iterator<ConversationContext<?>> iterator = ctxStack.iterator();

        // first item is rendered as ui-icon-home => we don't want it so we disable it.
        MenuItem menuItem = new MenuItem();
        menuItem.setRendered(false);
        model.addMenuItem(menuItem);

        int i = 0;
        while (iterator.hasNext()) {
            ConversationContext<?> ctx = iterator.next();
            if (isNotBlank(ctx.getLabel())) {
                menuItem = new MenuItem();
                menuItem.setValue(ctx.getLabel());
                if (i == beforeLastIndex && beforeLastIndex > 0) {
                    // calls back button action
                    menuItem.setOnclick("APP.menu.back()");
                } else {
                    menuItem.setDisabled(true);
                }

                model.addMenuItem(menuItem);
            }
            i++;
        }
        return model;
    }

    // --------------------------------------------
    // Impl details
    // --------------------------------------------    

    /**
     * Holds the current user's conversations.
     * Note: When calling this method, you must be sure that the FacesContext is present. For example, FacesContext is not present in ConversationFilter.
     */
    private Map<String, Conversation> conversationMap() {
        @SuppressWarnings("unchecked")
        Map<String, Conversation> map = (Map<String, Conversation>) sessionMap().get(CONVERSATION_MAP);
        if (map == null) {
            map = newConversationOrderedMap();
            sessionMap().put(CONVERSATION_MAP, map);
        }
        return map;
    }

    private Map<String, Conversation> conversationMap(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<String, Conversation> map = (Map<String, Conversation>) session.getAttribute(CONVERSATION_MAP);
        if (map == null) {
            map = newConversationOrderedMap();
            session.setAttribute(CONVERSATION_MAP, map);
        }
        return map;
    }

    /**
     * Constructs an ordered map so we can evict conversation on a FIFO basis.
     * We rely on an Integer comparator as String comparison would not be exact.
     */
    private Map<String, Conversation> newConversationOrderedMap() {
        return newTreeMap(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
            }
        });
    }

    private Map<String, Object> sessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    // --------------------------------------------
    // Support for conversation listeners
    // --------------------------------------------    

    private Collection<ConversationListener> getConversationListeners() {
        if (conversationListeners == null) {
            conversationListeners = applicationContext.getBeansOfType(ConversationListener.class).values();

        }
        return conversationListeners;
    }

    private void conversationCreated(Conversation conversation) {
        for (ConversationListener cl : getConversationListeners()) {
            cl.conversationCreated(conversation);
        }
    }

    private void conversationPausing(Conversation conversation) {
        for (ConversationListener cl : getConversationListeners()) {
            cl.conversationPausing(conversation);
        }
    }

    private void conversationResuming(Conversation conversation, HttpServletRequest request) {
        for (ConversationListener cl : getConversationListeners()) {
            cl.conversationResuming(conversation, request);
        }
    }

    private void conversationEnding(Conversation conversation) {
        for (ConversationListener cl : getConversationListeners()) {
            cl.conversationEnding(conversation);
        }
    }
}
