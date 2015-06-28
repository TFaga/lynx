package com.github.tfaga.lynx.exceptions;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class NoSuchEntityFieldException extends IllegalArgumentException {

    private String field;
    private String entity;

    public NoSuchEntityFieldException(String msg, String field, String entity) {

        super(msg);

        this.field = field;
        this.entity = entity;
    }

    public String getField() {
        return field;
    }

    public String getEntity() {
        return entity;
    }
}
