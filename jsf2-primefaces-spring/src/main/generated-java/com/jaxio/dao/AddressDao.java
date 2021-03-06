/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/main/java/project/dao/DAOImpl.e.vm.java
 */
package com.jaxio.dao;

import static com.jaxio.domain.Address_.city;
import javax.inject.Named;
import javax.inject.Singleton;
import com.jaxio.dao.AddressDao;
import com.jaxio.dao.support.GenericDao;
import com.jaxio.domain.Address;

/**
 * JPA 2 Data Access Object for {@link Address}.
 * Note: do not use @Transactional in the DAO layer.
 */
@Named
@Singleton
public class AddressDao extends GenericDao<Address, Integer> {
    public AddressDao() {
        super(Address.class, city);
    }
}