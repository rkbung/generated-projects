/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package com.jaxio.web.selenium.page.document;

import static org.openqa.selenium.By.name;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jaxio.web.selenium.support.Page;
import com.jaxio.web.selenium.page.AbstractSearchPage;

@Page
public class DocumentSearchPage extends AbstractSearchPage {
    // search box
    @FindBy(id = "form:ownerSelector_input")
    public WebElement owner;

    public void edit(String document) {
        clickEdit(document);
    }

    public void add(String document) {
        clickAdd(document);
    }

    public void delete(String document) {
        clickDelete(document);
        webClient.click(name("form:askForDeleteItemDialogYes"));
    }

    public void select(String document) {
        clickSelect(document);
    }
}