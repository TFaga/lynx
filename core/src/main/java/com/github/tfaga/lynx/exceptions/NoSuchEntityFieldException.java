package com.github.tfaga.lynx.exceptions;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class NoSuchEntityFieldException extends IllegalArgumentException {

    private String field;

    public NoSuchEntityFieldException(String msg, String field) {

        super(msg);

        this.field = field;
    }

    public String getField() {
        return field;
    }
}
