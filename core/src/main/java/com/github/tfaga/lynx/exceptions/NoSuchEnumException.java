package com.github.tfaga.lynx.exceptions;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class NoSuchEnumException extends RuntimeException {

    private String field;
    private String value;

    public NoSuchEnumException(String msg, String field, String value) {

        super(msg);

        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
