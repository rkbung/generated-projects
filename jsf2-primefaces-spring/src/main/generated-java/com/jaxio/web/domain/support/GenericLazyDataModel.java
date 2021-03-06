/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/GenericLazyDataModel.p.vm.java
 */
package com.jaxio.web.domain.support;

import static com.jaxio.web.conversation.ConversationHolder.getCurrentConversation;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.jaxio.dao.support.OrderBy;
import com.jaxio.dao.support.OrderByDirection;
import com.jaxio.dao.support.SearchParameters;
import com.jaxio.domain.Identifiable;
import com.jaxio.printer.TypeAwarePrinter;
import com.jaxio.repository.support.GenericRepository;
import com.jaxio.util.ResourcesUtil;
import com.jaxio.web.conversation.ConversationContext;
import com.jaxio.web.conversation.ConversationCallBack;
import com.jaxio.web.util.MessageUtil;
import com.jaxio.web.util.PrimeFacesUtil;

/**
 * Extends PrimeFaces {@link LazyDataModel} in order to support server-side pagination, row selection, multi select etc.
 */
public abstract class GenericLazyDataModel<E extends Identifiable<PK>, PK extends Serializable, F extends GenericSearchForm<E, PK, F>> extends LazyDataModel<E> {
    private static final long serialVersionUID = 1L;

    @Inject
    protected ResourcesUtil resourcesUtil;
    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TypeAwarePrinter printer;

    private E selectedRow;
    private E[] selectedRows;
    private boolean bypassFirstOffset = true;

    protected GenericRepository<E, PK> repository;
    protected Converter converter;
    protected GenericController<E, PK> controller;
    protected GenericSearchForm<E, PK, F> searchForm;

    public GenericLazyDataModel(GenericRepository<E, PK> repository, Converter converter, GenericController<E, PK> controller,
            GenericSearchForm<E, PK, F> searchForm) {
        this.repository = repository;
        this.converter = converter;
        this.controller = controller;
        this.searchForm = searchForm;
    }

    @Override
    public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        SearchParameters sp = searchForm.toSearchParameters();
        E example = searchForm.getEntity();
        setRowCount(repository.findCount(example, sp)); // total count so the paginator may display the total number of pages
        populateSearchParameters(sp, first, pageSize, sortField, sortOrder, filters); // load one page of data        
        return repository.find(example, sp);
    }

    /**
     * _HACK_
     * Call it from your view when a 'search' event is triggered to bypass offset sent by primefaces paginator.
     */
    public void onSearch() {
        bypassFirstOffset = true;
    }

    @Override
    public void setRowCount(int rowCount) {
        super.setRowCount(rowCount);
        PrimeFacesUtil.updateSearchResultsRegion(resourcesUtil.getPluralableProperty("search_results_status", rowCount));
    }

    /**
     * Applies the passed parameters to the passed SearchParameters.
     * Note: filters are not supported for the moment
     * @return the passed searchParameters
     */
    protected SearchParameters populateSearchParameters(SearchParameters sp, int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        sp.setFirstResult(bypassFirstOffset ? 0 : first);
        bypassFirstOffset = false;
        sp.setMaxResults(pageSize);

        sp.clearOrders();
        if (isNotEmpty(sortField)) {
            return sp.orderBy(new OrderBy(sortField, convert(sortOrder)));
        } else {
            return controller.defaultOrder(sp);
        }
    }

    // ---------------------
    // Select row
    // ---------------------

    /**
     * Returns the currently selected row.
     */
    public E getSelectedRow() {
        return selectedRow;
    }

    /**
     * Set the currently selected row.
     */
    public void setSelectedRow(E selectedRow) {
        this.selectedRow = selectedRow;
    }

    /**
     * Set the selectedRow to null.
     */
    public void resetSelectedRow() {
        this.selectedRow = null;
    }

    // ---------------------
    // Multi select
    // ---------------------

    public void setSelectedRows(E[] selectedRows) {
        this.selectedRows = selectedRows;
    }

    public E[] getSelectedRows() {
        return selectedRows;
    }

    public String multiSelect() {
        return getCallBack().selected(getSelectedRows());
    }

    /**
     * Convert PrimeFaces SortOrder to our OrderByDirection.
     */
    protected OrderByDirection convert(SortOrder order) {
        return order == SortOrder.DESCENDING ? OrderByDirection.DESC : OrderByDirection.ASC;
    }

    // ---------------------
    // Actions
    // ---------------------

    /**
     * Action to create a new entity.
     */
    public String create() {
        return controller.create();
    }

    /**
     * Action to edit the selected entity.
     */
    public String edit() {
        return controller.edit(getRowData());
    }

    /**
     * Action to view the selected entity.
     */
    public String view() {
        return controller.view(getRowData());
    }

    /**
     * Action invoked from sub search pages used to select the target of an association.
     */
    public String select() {
        return select(getRowData());
    }

    protected String select(E selectedRow) {
        return getCallBack().selected(selectedRow);
    }

    /**
     * React to mouse click and force the navigation depending on the context.
     * When in sub mode, the select action is invoked otherwise the edit action is invoked.
     */
    public void onRowSelect(SelectEvent event) {
        E selected = getSelectedRow();
        if (selected != null) {
            if (getCurrentConversation().getCurrentContext().isSub()) {
                Faces.navigate(controller.select(selected));
            } else if (controller.getPermission().canEdit(selected)) {
                Faces.navigate(controller.edit(selected));
            } else {
                Faces.navigate(controller.view(selected));
            }
        }
    }

    /**
     * Ajax action listener to delete the selected entity.
     */
    public void delete() {
        E selected = getSelectedRow();
        if (selected != null) {
            repository.delete(selected);
            messageUtil.infoEntity("status_deleted_ok", selected);
            resetSelectedRow();
        }
    }

    @Override
    public String getRowKey(E item) {
        return String.valueOf(item.hashCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getRowData(String rowKey) {
        for (E item : ((List<E>) getWrappedData())) {
            if (rowKey.equals(getRowKey(item))) {
                return item;
            }
        }
        return null;
    }

    private ConversationCallBack<E> getCallBack() {
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack();
    }

    public void onExcel() throws IOException {
        controller.onExcel(searchForm.toSearchParameters());
    }
}