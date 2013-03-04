/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/LazyDataModel.e.vm.java
 */
package com.jaxio.web.domain;

import static com.jaxio.web.conversation.ConversationHolder.getCurrentConversation;
import java.util.Arrays;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import com.jaxio.domain.Address;
import com.jaxio.repository.AddressRepository;
import com.jaxio.repository.support.Repository;
import com.jaxio.web.conversation.ConversationContext;
import com.jaxio.web.converter.AddressJsfConverter;
import com.jaxio.web.domain.support.GenericLazyDataModel;
import com.jaxio.web.domain.support.GenericSearchForm;

/**
 * Provides server-side pagination for search.
 * TODO: dependencies wiring after deserialization (get inspiration from http://jira.springframework.org/browse/SWF-1224 )
 */
@Named
@Scope("conversation")
public class AddressLazyDataModel extends GenericLazyDataModel<Address, Integer, AddressSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    transient private AddressRepository addressRepository;
    @Inject
    transient private AddressJsfConverter addressConverter;
    @Inject
    transient private AddressSearchForm addressSearchForm;

    private Address[] selectedRows;

    @Override
    protected Repository<Address, Integer> getRepository() {
        return addressRepository;
    }

    @Override
    protected Converter getConverter() {
        return addressConverter;
    }

    @Override
    protected GenericSearchForm<Address, AddressSearchForm> getSearchForm() {
        return addressSearchForm;
    }

    @Override
    protected ConversationContext<Address> getSelectedContext(Address selected) {
        if (selected.isIdSet()) {
            // just the id matters as we want to reload it in the conversation entity manager.
            return AddressController.newEditContext(selected.getId());
        } else {
            return AddressController.newEditContext(selected); // fresh entity (creation)
        }
    }

    // -----------------------------------
    // Multi selection support
    // -----------------------------------

    public void setSelectedRows(Address[] selectedRows) {
        this.selectedRows = selectedRows;
    }

    public Address[] getSelectedRows() {
        return selectedRows;
    }

    public String multiSelect() {
        return getCurrentConversation() //
                .<ConversationContext<Address>> getCurrentContext() //
                .getCallBack() //
                .selected(Arrays.asList(selectedRows));
    }
}