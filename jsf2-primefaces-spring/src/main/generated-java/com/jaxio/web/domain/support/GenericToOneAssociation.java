/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/GenericToOneAssociation.p.vm.java
 */
package com.jaxio.web.domain.support;

import java.io.Serializable;

import com.jaxio.domain.Identifiable;
import com.jaxio.web.conversation.ConversationCallBack;
import com.jaxio.web.permission.support.GenericPermission;
import com.jaxio.web.util.MessageUtil;

/**
 * Controller that allows you to manage an entity's x-to-one association.
 */
public abstract class GenericToOneAssociation<E extends Identifiable<PK>, PK extends Serializable> {
    protected GenericController<E, PK> controller;
    protected GenericPermission<E> permission;
    protected MessageUtil messageUtil;
    protected String labelKey;

    public GenericToOneAssociation(String labelKey, GenericController<E, PK> controller) {
        this.labelKey = labelKey;
        this.controller = controller;
        this.messageUtil = controller.getMessageUtil();
        this.permission = controller.getPermission();
    }

    protected abstract E get();

    protected abstract void set(E e);

    public String view() {
        return controller.editSubReadOnlyView(labelKey, get());
    }

    /**
     * Helper for the autoComplete component.
     */
    public E getSelected() {
        return get();
    }

    /**
     * Handles ajax autoComplete event and regular page postback.
     */
    public void setSelected(E selected) {
        if (controller.shouldReplace(get(), selected)) {
            set(selected);
        }
    }

    public String search() {
        return select();
    }

    public String select() {
        return controller.selectSubView(labelKey, selectCallBack);
    }

    protected ConversationCallBack<E> selectCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSelected(E e) {
            checkPermission(permission.canSelect(e));
            set(e);
            messageUtil.infoEntity("status_selected_ok", get());
        }
    };

    public String add() {
        return controller.createSubView(labelKey, addCallBack);
    }

    protected ConversationCallBack<E> addCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onOk(E e) {
            checkPermission(permission.canCreate());
            set(e);
            messageUtil.infoEntity("status_created_ok", e);
        }
    };

    public String edit() {
        return controller.editSubView(labelKey, get(), editCallBack);
    }

    protected ConversationCallBack<E> editCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onOk(E e) {
            set(e);
            messageUtil.infoEntity("status_edited_ok", e);
        }
    };

    protected void checkPermission(boolean check) {
        if (!check) {
            throw new IllegalStateException();
        }
    }
}
