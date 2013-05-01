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
import com.jaxio.domain.Role;
import com.jaxio.repository.RoleRepository;
import com.jaxio.web.domain.support.GenericEditForm;
import com.jaxio.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Role}.
 */
@Named
@ConversationContextScoped
public class RoleEditForm extends GenericEditForm<Role, Integer> {

    @Inject
    public RoleEditForm(RoleRepository roleRepository) {
        super(roleRepository);
    }

    /**
     * The entity to edit/view.
     */
    public Role getRole() {
        return getEntity();
    }
}
