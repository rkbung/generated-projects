/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/converter/JsfConverter.e.vm.java
 */
package com.jaxio.web.converter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import com.jaxio.domain.Role;
import com.jaxio.repository.RoleRepository;
import com.jaxio.web.converter.GenericJsfConverter;

/**
 * JSF Converter for {@link Role}.
 */
@Named
@Singleton
public class RoleJsfConverter extends GenericJsfConverter<Role, Integer> {
    @Inject
    public RoleJsfConverter(RoleRepository roleRepository) {
        super(roleRepository, Role.class, Integer.class);
    }
}