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
import com.jaxio.domain.Document;
import com.jaxio.repository.DocumentRepository;
import com.jaxio.web.converter.GenericJsfConverter;

/**
 * JSF converter for {@link Document}.
 * @see GenericJsfConverter
 */
@Named
@Singleton
public class DocumentJsfConverter extends GenericJsfConverter<Document, String> {
    @Inject
    public DocumentJsfConverter(DocumentRepository documentRepository) {
        super(documentRepository, Document.class, String.class);
    }
}