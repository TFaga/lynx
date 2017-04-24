package com.github.tfaga.lynx.utils;

import java.net.URI;

/**
 * @author Tilen Faganel
 */
public class QueryStringDefaults {

    private Boolean paginationEnabled = true;
    private Boolean filtersEnabled = true;
    private Boolean orderEnabled = true;
    private Boolean fieldsEnabled = true;

    private Long maxLimit = 100L;
    private Long defaultLimit = 10L;
    private Long defaultOffset = 0L;

    public QueryStringDefaults enablePagination(Boolean enable) {

        paginationEnabled = enable;

        return this;
    }

    public QueryStringDefaults enableFilters(Boolean enable) {

        filtersEnabled = enable;

        return this;
    }

    public QueryStringDefaults enableOrder(Boolean enable) {

        orderEnabled = enable;

        return this;
    }

    public QueryStringDefaults enableFields(Boolean enable) {

        fieldsEnabled = enable;

        return this;
    }

    public QueryStringDefaults maxLimit(int limit) {

        return maxLimit((long) limit);
    }

    public QueryStringDefaults maxLimit(Long limit) {

        maxLimit = limit;

        return this;
    }

    public QueryStringDefaults defaultLimit(int limit) {

        return defaultLimit((long) limit);
    }

    public QueryStringDefaults defaultLimit(Long limit) {

        defaultLimit = limit;

        return this;
    }

    public QueryStringDefaults defaultOffset(int offset) {

        return defaultOffset((long) offset);
    }

    public QueryStringDefaults defaultOffset(Long offset) {

        defaultOffset = offset;

        return this;
    }

    public QueryStringBuilder builder() {
        return new QueryStringBuilder()
                .maxLimit(maxLimit)
                .defaultLimit(defaultLimit)
                .defaultOffset(defaultOffset)
                .enablePagination(paginationEnabled)
                .enableFilters(filtersEnabled)
                .enableOrder(orderEnabled)
                .enableFields(fieldsEnabled);
    }
}
