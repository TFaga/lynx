package com.github.tfaga.lynx.beans;

import com.github.tfaga.lynx.enums.FilterOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tilen Faganel
 */
public class QueryFilter {

    private String field;

    private FilterOperation operation;

    private String value;

    private Date dateValue;

    private List<String> values;

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
