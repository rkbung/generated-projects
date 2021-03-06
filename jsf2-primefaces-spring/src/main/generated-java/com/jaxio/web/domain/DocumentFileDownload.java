/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/FileDownload.e.vm.java
 */
package com.jaxio.web.domain;

import java.io.ByteArrayInputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.transaction.annotation.Transactional;
import com.jaxio.domain.Document;
import com.jaxio.repository.DocumentRepository;

/**
 * Stateless controller to download {@link Document} binaries 
 */
@Named
@Singleton
public class DocumentFileDownload {
    @Inject
    private DocumentRepository documentRepository;

    /**
     * Primefaces support for documentBinary file download, this method is transactional so the binary can be lazily loaded
     */
    @Transactional(readOnly = true)
    public StreamedContent getDocumentBinaryStream(Document detachedDocument) {
        Document document = null;
        if (detachedDocument.getDocumentBinary() != null) {
            document = detachedDocument;
        } else {
            document = documentRepository.get(detachedDocument);
        }
        return new DefaultStreamedContent( //
                new ByteArrayInputStream(document.getDocumentBinary()), //
                document.getDocumentContentType(), //
                document.getDocumentFileName());
    }
}