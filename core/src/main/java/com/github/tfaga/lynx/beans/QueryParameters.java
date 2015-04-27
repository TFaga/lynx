package com.github.tfaga.lynx.beans;

/**
 * @author Tilen Faganel
 */
public class QueryParameters {

    private Long limit;

    private Long offset;

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
