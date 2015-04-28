package com.github.tfaga.lynx.exceptions;

/**
 * @author Tilen Faganel
 */
public class QueryFormatException extends RuntimeException {

    private String field;

    public QueryFormatException(String field) {

        super("Field '" + field.toLowerCase() + "' in the query string is " +
                "in the wrong format");

        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
