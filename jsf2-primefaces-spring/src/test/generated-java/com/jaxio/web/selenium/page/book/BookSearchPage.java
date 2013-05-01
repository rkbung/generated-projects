/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package com.jaxio.web.selenium.page.book;

import static org.openqa.selenium.By.name;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jaxio.web.selenium.support.Page;
import com.jaxio.web.selenium.page.AbstractSearchPage;

@Page
public class BookSearchPage extends AbstractSearchPage {
    // search box
    @FindBy(id = "form:title_input")
    public WebElement title;
    @FindBy(id = "form:numberOfPagesFrom_input")
    public WebElement numberOfPagesFrom;
    @FindBy(id = "form:numberOfPagesTo_input")
    public WebElement numberOfPagesTo;
    @FindBy(id = "form:ownerSelector_input")
    public WebElement owner;

    public void editBook(String book) {
        clickEdit(book);
    }

    public void addBook(String book) {
        clickAdd(book);
    }

    public void deleteBook(String book) {
        clickDelete(book);
        client.click(name("form:askForRemoveBookDialogYes"));
    }

    public void selectBook(String book) {
        clickSelect(book);
    }
}