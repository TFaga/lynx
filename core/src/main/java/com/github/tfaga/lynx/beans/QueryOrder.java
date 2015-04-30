package com.github.tfaga.lynx.beans;

import com.github.tfaga.lynx.enums.OrderDirection;

/**
 * @author Tilen Faganel
 */
public class QueryOrder {

    private String field;

    private OrderDirection order;

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
