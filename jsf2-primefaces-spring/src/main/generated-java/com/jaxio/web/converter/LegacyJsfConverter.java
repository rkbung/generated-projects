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
import com.jaxio.domain.Legacy;
import com.jaxio.domain.LegacyPk;
import com.jaxio.repository.LegacyRepository;
import com.jaxio.web.converter.GenericJsfConverter;

/**
 * JSF Converter for {@link Legacy}.
 */
@Named
@Singleton
public class LegacyJsfConverter extends GenericJsfConverter<Legacy, LegacyPk> {
    @Inject
    public LegacyJsfConverter(LegacyRepository legacyRepository) {
        super(legacyRepository, Legacy.class, LegacyPk.class);
    }
}