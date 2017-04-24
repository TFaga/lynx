package com.github.tfaga.lynx.exceptions;

/**
 * @author Tilen Faganel
 * @since 1.0.0
 */
public class InvalidEntityFieldException extends RuntimeException {

    private String field;
    private String entity;

    public InvalidEntityFieldException(String message, String field, String entity) {
        super(message);

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
