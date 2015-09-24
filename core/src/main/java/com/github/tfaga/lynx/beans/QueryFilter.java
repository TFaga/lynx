package com.github.tfaga.lynx.beans;

import com.github.tfaga.lynx.enums.FilterOperation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryFilter implements Serializable {

    private final static long serialVersionUID = 1L;

    private String field;

    private FilterOperation operation;

    private String value;

    private Date dateValue;

    private List<String> values;

    public QueryFilter() {
    }

    public QueryFilter(String field, FilterOperation operation) {
        this.field = field;
        this.operation = operation;
    }

    public QueryFilter(String field, FilterOperation operation, String value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    public QueryFilter(String field, FilterOperation operation, Date dateValue) {
        this.field = field;
        this.operation = operation;
        this.dateValue = dateValue;
    }

    public QueryFilter(String field, FilterOperation operation, List<String> values) {
        this.field = field;
        this.operation = operation;
        this.values = values;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public FilterOperation getOperation() {
        return operation;
    }

    public void setOperation(FilterOperation operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {

        if (values == null)
            values = new ArrayList<>();

        return values;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }
}
