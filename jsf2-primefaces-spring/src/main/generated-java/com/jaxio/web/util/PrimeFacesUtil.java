/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/util/PrimeFacesUtil.p.vm.java
 */
package com.jaxio.web.util;

import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

/**
 * Use this bean to execute JavaScript on client side.
 */
public class PrimeFacesUtil {

    /**
     * Tells the client to update the search results region with the passed text.
     */
    public static void updateSearchResultsRegion(String text) {
        if (RequestContext.getCurrentInstance() != null) {
            RequestContext.getCurrentInstance().execute("APP.updateSearchResultsRegion(\"" + text + "\")");
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static void forceClose() {
        RequestContext.getCurrentInstance().execute("APP.menu.forceClose()");
    }
}