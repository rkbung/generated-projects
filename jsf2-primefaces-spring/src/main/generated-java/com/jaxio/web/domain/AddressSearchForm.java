/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/SearchForm.e.vm.java
 */
package com.jaxio.web.domain;

import static com.jaxio.dao.support.PropertySelector.newPropertySelector;
import javax.inject.Named;
import com.jaxio.dao.support.PropertySelector;
import com.jaxio.dao.support.SearchParameters;
import com.jaxio.domain.Address;
import com.jaxio.domain.Address_;
import com.jaxio.web.domain.support.GenericSearchForm;
import com.jaxio.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Address}.
 * It exposes a {@link Address} instance so it can be used in search by Example query.
 */
@Named
@ConversationContextScoped
public class AddressSearchForm extends GenericSearchForm<Address, Integer, AddressSearchForm> {
    private static final long serialVersionUID = 1L;

    protected Address address = new Address();
    protected PropertySelector<Address, String> streetNameSelector = newPropertySelector(Address_.streetName);
    protected PropertySelector<Address, String> citySelector = newPropertySelector(Address_.city);

    public Address getAddress() {
        return address;
    }

    @Override
    protected Address getEntity() {
        return getAddress();
    }

    @Override
    public AddressSearchForm newInstance() {
        return new AddressSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        return new SearchParameters() //
                .anywhere() //
                .term(term) //
                .property(streetNameSelector) //
                .property(citySelector) //
        ;
    }

    @Override
    public void resetWithOther(AddressSearchForm other) {
        this.address = other.getAddress();
        this.term = other.getTerm();
        this.streetNameSelector = other.getStreetNameSelector();
        this.citySelector = other.getCitySelector();
    }

    // Property selectors
    public PropertySelector<Address, String> getStreetNameSelector() {
        return streetNameSelector;
    }

    public PropertySelector<Address, String> getCitySelector() {
        return citySelector;
    }
}
