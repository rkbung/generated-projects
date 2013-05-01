/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/GraphLoader.e.vm.java
 */
package com.jaxio.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import com.jaxio.domain.Book;
import com.jaxio.repository.BookRepository;
import com.jaxio.repository.support.EntityGraphLoader;

/**
 * Preloads the {@link Book} associations required by the view layer.
 */
@Named
@Singleton
public class BookGraphLoader extends EntityGraphLoader<Book, Integer> {
    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected BookGraphLoader() {
        super();
    }

    @Inject
    public BookGraphLoader(BookRepository bookRepository) {
        super(bookRepository);
    }

    @Override
    public void loadGraph(Book book) {
        loadSingular(book.getOwner());
    }
}