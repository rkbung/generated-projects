/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-jpa:src/main/java/project/printer/Printer.e.vm.java
 */
package com.jaxio.printer.more;

import java.util.Locale;
import javax.inject.Named;
import javax.inject.Singleton;
import com.jaxio.domain.more.MoreTypesDemo;
import com.jaxio.printer.DiscoverablePrinter;

/**
 * {@link org.springframework.format.Printer} for {@link MoreTypesDemo} 
 *
 * @see org.springframework.format.Printer
 * @see DiscoverablePrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class MoreTypesDemoPrinter extends DiscoverablePrinter<MoreTypesDemo> {
    public MoreTypesDemoPrinter() {
        super(MoreTypesDemo.class);
    }

    @Override
    public String print(MoreTypesDemo moreTypesDemo, Locale locale) {
        if (moreTypesDemo == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, moreTypesDemo.getNumberInt());
        appendIfNotEmpty(ret, moreTypesDemo.getNumberLong());
        appendIfNotEmpty(ret, moreTypesDemo.getNumberDouble());
        return ret.toString();
    }
}