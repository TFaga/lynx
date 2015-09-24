package com.github.tfaga.lynx.beans;

import com.github.tfaga.lynx.enums.OrderDirection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryOrder implements Serializable {

    private final static long serialVersionUID = 1L;

    private String field;

    private OrderDirection order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryOrder order = (QueryOrder) o;
        return Objects.equals(field, order.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderDirection getOrder() {
        return order;
    }

    public void setOrder(OrderDirection order) {
        this.order = order;
    }
}
