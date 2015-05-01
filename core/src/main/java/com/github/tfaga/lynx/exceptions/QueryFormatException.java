package com.github.tfaga.lynx.exceptions;

import com.github.tfaga.lynx.enums.QueryFormatError;

/**
 * @author Tilen Faganel
 */
public class QueryFormatException extends RuntimeException {

    private String field;

    private QueryFormatError reason;

    public QueryFormatException(String field) {

        super("Field '" + field.toLowerCase() + "' in the query string is " +
                "in the wrong format");

        this.field = field;
    }

    public QueryFormatException(String field, QueryFormatError reason) {

        super("Field '" + field.toLowerCase() + "' in the query string is " +
                "in the wrong format for reason: " + reason);

        this.field = field;
        this.reason = reason;
    }

    public String getField() {
        return field;
    }

    public QueryFormatError getReason() {
        return reason;
    }
}
