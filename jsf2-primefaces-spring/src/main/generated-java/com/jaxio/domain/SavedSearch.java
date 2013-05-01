/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-jpa:src/main/java/project/domain/Entity.e.vm.java
 */
package com.jaxio.domain;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Objects;
import com.jaxio.domain.Account;
import com.jaxio.domain.IdentifiableHashBuilder;
import com.jaxio.domain.SavedSearch_;

@Entity
@Table(name = "SAVED_SEARCH")
@FilterDef(name = "mySavedSearchFilter", defaultCondition = "ACCOUNT_ID = :currentAccountId ", parameters = @ParamDef(name = "currentAccountId", type = "org.hibernate.type.StringType"))
@Filter(name = "mySavedSearchFilter")
public class SavedSearch implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SavedSearch.class);

    // Raw attributes
    private Integer id; // pk
    private String name; // not null
    private String formClassname; // not null
    private byte[] formContent;

    // Many to one
    private Account account; // not null (accountId)

    // -- [id] ------------------------

    @Override
    @Column(name = "ID", precision = 10)
    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public SavedSearch id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    @XmlTransient
    public boolean isIdSet() {
        return id != null;
    }

    // -- [name] ------------------------

    @Size(max = 128)
    @NotEmpty
    @Column(name = "NAME", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SavedSearch name(String name) {
        setName(name);
        return this;
    }

    // -- [formClassname] ------------------------

    @Size(max = 256)
    @NotEmpty
    @Column(name = "FORM_CLASSNAME", nullable = false, length = 256)
    public String getFormClassname() {
        return formClassname;
    }

    public void setFormClassname(String formClassname) {
        this.formClassname = formClassname;
    }

    public SavedSearch formClassname(String formClassname) {
        setFormClassname(formClassname);
        return this;
    }

    // -- [formContent] ------------------------

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "FORM_CONTENT")
    @Lob
    public byte[] getFormContent() {
        return formContent;
    }

    public void setFormContent(byte[] formContent) {
        this.formContent = formContent;
    }

    public SavedSearch formContent(byte[] formContent) {
        setFormContent(formContent);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: SavedSearch.accountId ==> Account.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Account getAccount() {
        return account;
    }

    /**
     * Set the {@link #account} without adding this SavedSearch instance on the passed {@link #account}
     * If you want to preserve referential integrity we recommend to use
     * instead the corresponding adder method provided by {@link Account}
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    public SavedSearch account(Account account) {
        setAccount(account);
        return this;
    }

    /**
     * Set the default values.
     */
    public void initDefaultValues() {
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof SavedSearch && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this SavedSearch instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add(SavedSearch_.id.getName(), getId()) //
                .add(SavedSearch_.name.getName(), getName()) //
                .add(SavedSearch_.formClassname.getName(), getFormClassname()) //
                .add(SavedSearch_.formContent.getName(), getFormContent()) //
                .toString();
    }
}