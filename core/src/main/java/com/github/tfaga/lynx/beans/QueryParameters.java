package com.github.tfaga.lynx.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tilen Faganel
 */
public class QueryParameters {

    private Long limit;

    private Long offset;

    private List<QueryOrder> order;

    private List<String> fields;

    private List<QueryFilter> filters;

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit.longValue();
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset.longValue();
    }

    public List<QueryOrder> getOrder() {

        if (order == null)
            order = new ArrayList<>();

        return order;
    }

    public List<String> getFields() {

        if (fields == null)
            fields = new ArrayList<>();

        return fields;
    }

    public List<QueryFilter> getFilters() {

        if (filters == null)
            filters = new ArrayList<>();

        return filters;
    }
}
