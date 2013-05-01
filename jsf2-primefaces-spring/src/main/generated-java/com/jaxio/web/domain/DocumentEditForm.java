/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/EditForm.e.vm.java
 */
package com.jaxio.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import com.jaxio.domain.Account;
import com.jaxio.domain.Document;
import com.jaxio.repository.DocumentRepository;
import com.jaxio.web.domain.AccountController;
import com.jaxio.web.domain.DocumentGraphLoader;
import com.jaxio.web.domain.support.GenericEditForm;
import com.jaxio.web.faces.Conversation;

/**
 * View Helper/Controller to edit {@link Document}.
 */
@Named
@Conversation
public class DocumentEditForm extends GenericEditForm<Document, String> {
    @Inject
    public void setDocumentRepository(DocumentRepository documentRepository) {
        setRepository(documentRepository);
    }

    @Inject
    public void setDocumentGraphLoader(DocumentGraphLoader documentGraphLoader) {
        setEntityGraphLoader(documentGraphLoader);
    }

    public Document getDocument() {
        return getEntity();
    }

    // --------------------------------------------
    // Actions for account association
    // --------------------------------------------
    @Inject
    private AccountController accountController;

    public String viewAccount() {
        return accountController.editSubReadOnlyView("document_account", getDocument().getAccount());
    }

    /**
     * Helper for the autoComplete component used for the Document's account property.
     */
    public Account getSelectedAccount() {
        return getDocument().getAccount();
    }

    /**
     * Helper for the autoComplete component used for the Document's account property.
     * Handles ajax autoComplete event and regular page postback.
     */
    public void setSelectedAccount(Account account) {
        if (accountController.shouldReplace(getDocument().getAccount(), account)) {
            getDocument().setAccount(account);
        }
    }
}