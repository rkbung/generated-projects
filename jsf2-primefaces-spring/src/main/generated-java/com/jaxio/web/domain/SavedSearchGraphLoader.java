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
import com.jaxio.domain.SavedSearch;
import com.jaxio.repository.SavedSearchRepository;
import com.jaxio.repository.support.EntityGraphLoader;

/**
 * Preloads the {@link SavedSearch} associations required by the view layer.
 */
@Named
@Singleton
public class SavedSearchGraphLoader extends EntityGraphLoader<SavedSearch, Integer> {
    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected SavedSearchGraphLoader() {
        super();
    }

    @Inject
    public SavedSearchGraphLoader(SavedSearchRepository savedSearchRepository) {
        super(savedSearchRepository);
    }

    @Override
    public void loadGraph(SavedSearch savedSearch) {
        loadSingular(savedSearch.getAccount());
    }
}