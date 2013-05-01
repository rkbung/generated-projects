/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/GenericLazyDataModel.p.vm.java
 */
package com.jaxio.web.domain.support;

import static com.jaxio.web.conversation.ConversationHolder.getCurrentConversation;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.apache.commons.lang.WordUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.jaxio.dao.support.OrderBy;
import com.jaxio.dao.support.OrderByDirection;
import com.jaxio.dao.support.SearchParameters;
import com.jaxio.domain.Identifiable;
import com.jaxio.printer.TypeAwarePrinter;
import com.jaxio.repository.support.Repository;
import com.jaxio.util.ResourcesUtil;
import com.jaxio.web.conversation.ConversationContext;
import com.jaxio.web.util.MessageUtil;
import com.jaxio.web.util.PrimeFacesUtil;
import com.jaxio.context.UserContext;

/**
 * Extends PrimeFaces {@link LazyDataModel} in order to support server-side pagination, row selection, multi select etc.
 */
public abstract class GenericLazyDataModel<E extends Identifiable<PK>, PK extends Serializable, F extends GenericSearchForm<E, PK, F>> extends LazyDataModel<E> {
    private static final long serialVersionUID = 1L;

    @Inject
    private ResourcesUtil resourcesUtil;
    @Inject
    private MessageUtil messageUtil;
    @Inject
    private TypeAwarePrinter printer;

    private E selectedRow;
    private E[] selectedRows;
    private boolean bypassFirstOffset = true;

    protected Repository<E, PK> repository;
    protected Converter converter;
    protected GenericController<E, PK> controller;
    protected GenericSearchForm<E, PK, F> searchForm;

    public GenericLazyDataModel() {
    }

    public GenericLazyDataModel(Repository<E, PK> repository, Converter converter, GenericController<E, PK> controller, GenericSearchForm<E, PK, F> searchForm) {
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
            sp.addOrderBy(new OrderBy(sortField, convert(sortOrder)));
        } else {
            defaultOrder(sp);
        }
        return sp;
    }

    protected void defaultOrder(SearchParameters sp) {

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
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack() //
                .selected(Arrays.asList(getSelectedRows()));
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

    public ConversationContext<E> getSelectedContext(E selected) {
        return controller.newEditContext(selected);
    }

    /**
     * Action to create a new entity.
     */
    public String sendNew() {
        E newEntity = repository.getNew();
        ConversationContext<E> ctx = getSelectedContext(newEntity);
        ctx.setLabel("Create new " + newEntity.getClass().getSimpleName());
        getCurrentConversation().setNextContext(ctx);
        return ctx.view();
    }

    /**
     * Action to edit the selected entity.
     */
    public String edit() {
        return edit(getRowData());
    }

    /**
     * support for edit() and onRowSelect methods 
     */
    protected String edit(E selectedRow) {
        ConversationContext<E> ctx = getSelectedContext(selectedRow);
        ctx.setLabel("Edit " + printer.print(selectedRow));
        getCurrentConversation().setNextContext(ctx);
        return ctx.view();
    }

    /**
     * Action to view the selected entity.
     */
    public String view() {
        ConversationContext<E> ctx = getSelectedContext(getRowData());
        ctx.setLabel("View " + printer.print(getRowData()));
        getCurrentConversation().setNextContextSubReadOnly(ctx);
        return ctx.view();
    }

    /**
     * Action invoked from sub search pages used to select the target of an association.
     */
    public String select() {
        return select(getRowData());
    }

    protected String select(E selectedRow) {
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack() //
                .selected(selectedRow);
    }

    /**
     * React to mouse click and force the navigation depending on the context.
     * When in sub mode, the select action is invoked otherwise the edit action is invoked.
     */
    public void onRowSelect(SelectEvent event) {
        E selected = getSelectedRow();
        if (selected != null) {
            if (getCurrentConversation().<ConversationContext<E>> getCurrentContext().isSub()) {
                Faces.navigate(select(selected));
            } else {
                Faces.navigate(edit(selected));
            }
        }
    }

    /**
     * Ajax action listener to delete the selected entity.
     */
    public void delete() {
        E selected = getSelectedRow();
        if (selected != null) {
            String infoArg = printer.print(selected);
            repository.delete(selected);
            messageUtil.info("status_deleted_ok", infoArg);
            resetSelectedRow();
        }
    }

    @Override
    public String getRowKey(E item) {
        return "" + item.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getRowData(String rowKey) {
        for (E item : ((List<E>) getWrappedData())) {
            if (rowKey.equals("" + item.hashCode())) {
                return item;
            }
        }
        return null;
    }

    // -------------
    // Excel related
    // -------------

    @Inject
    private ExcelExportService excelExportService;

    @Inject
    private ExcelExportSupport excelExportSupport;

    public void onExcel() throws IOException {
        SearchParameters searchParameters = searchForm.toSearchParameters();
        Map<String, Object> model = newHashMap();
        model.put("msg", resourcesUtil);
        model.put("search_date", excelExportSupport.dateToString(new Date()));
        model.put("search_by", UserContext.getUsername());
        model.put("searchParameters", searchParameters);
        model.put("searchParameters", searchParameters);
        excelObjects(model, searchParameters);
        excelExportService.export("excel/" + entityName().toLowerCase() + ".xlsx", model, excelOutputname());
    }

    protected String excelOutputname() {
        return entityName() + "-" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new Date()) + ".xlsx";
    }

    private String entityName() {
        return repository.getNew().getClass().getSimpleName();
    }

    protected void excelObjects(Map<String, Object> model, SearchParameters searchParameters) {
        excelExportSupport.convertSearchParametersToMap(model, searchParameters);

        int count = repository.findCount(searchParameters);
        model.put("search_nb_results", count);
        if (count > 65535) {
            searchParameters.maxResults(65535);
        }
        model.put(WordUtils.uncapitalize(entityName()), repository.find(searchParameters));
    }
}