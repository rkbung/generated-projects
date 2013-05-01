/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/LazyDataModel.e.vm.java
 */
package com.jaxio.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import com.jaxio.dao.support.SearchParameters;
import com.jaxio.domain.Account;
import com.jaxio.domain.Account_;
import com.jaxio.repository.AccountRepository;
import com.jaxio.web.converter.AccountJsfConverter;
import com.jaxio.web.domain.support.GenericLazyDataModel;
import com.jaxio.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Account}
 */
@Named
@ConversationContextScoped
public class AccountLazyDataModel extends GenericLazyDataModel<Account, String, AccountSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public AccountLazyDataModel(AccountRepository accountRepository, AccountJsfConverter accountConverter, AccountController accountController,
            AccountSearchForm accountSearchForm) {
        super(accountRepository, accountConverter, accountController, accountSearchForm);
    }

    @Override
    protected void defaultOrder(SearchParameters sp) {
        sp.orderBy(Account_.username);
    }
}