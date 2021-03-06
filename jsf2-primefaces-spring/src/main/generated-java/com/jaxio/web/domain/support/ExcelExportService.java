/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/ExcelExportService.p.vm.java
 */
package com.jaxio.web.domain.support;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;

@Named
public class ExcelExportService {

    private static final String EXCEL_CONTENT_TYPE = "application/vns.ms-excel";

    public void export(String template, Map<String, Object> model, String filename) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType(EXCEL_CONTENT_TYPE);
        externalContext.setResponseCharacterEncoding("UTF-8");
        externalContext.setResponseHeader("Content-Disposition", "attachement; filename=\"" + filename + "\"");
        try {
            export(template, model).write(externalContext.getResponseOutputStream());
        } catch (IOException e) {
            throw propagate(e);
        } finally {
            facesContext.responseComplete();
        }
    }

    public Workbook export(String templateClasspathLocation, Map<String, Object> model) {
        InputStream resource = null;
        try {
            resource = getResource(templateClasspathLocation);
            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformXLS(resource, model);
            autoResizeColumns(workbook);
            return workbook;
        } catch (Exception e) {
            throw propagate(e);
        } finally {
            closeQuietly(resource);
        }
    }

    private final Map<String, byte[]> resources = newHashMap();

    private InputStream getResource(String templateClasspathLocation) throws IOException {
        return new ByteArrayInputStream(resourceAsBytes(templateClasspathLocation));
    }

    private byte[] resourceAsBytes(String templateClasspathLocation) throws IOException {
        if (resources.containsKey(templateClasspathLocation)) {
            return resources.get(templateClasspathLocation);
        } else {
            byte[] templateAsStream = IOUtils.toByteArray(new ClassPathResource(templateClasspathLocation).getInputStream());
            resources.put(templateClasspathLocation, templateAsStream);
            return templateAsStream;
        }
    }

    private void autoResizeColumns(Workbook workbook) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int lastColumnIndex = findLastColumnIndex(sheet);
            for (int c = 0; c <= lastColumnIndex; c++) {
                sheet.autoSizeColumn(c);
            }
        }
    }

    private int findLastColumnIndex(Sheet sheet) {
        int max = 0;
        // FIXME: Pourquoi boucler sur les rows ?
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                max = Math.max(max, sheet.getRow(i).getLastCellNum());
            }
        }
        return max;
    }

}
