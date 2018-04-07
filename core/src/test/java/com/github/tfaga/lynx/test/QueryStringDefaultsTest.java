package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.utils.QueryStringDefaults;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryStringDefaultsTest {

    @Test
    public void testEmpty() {

        QueryParameters query = new QueryStringDefaults().builder().query("").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(10, query.getLimit().longValue());
        Assert.assertEquals(0, query.getOffset().longValue());
    }

    @Test
    public void testEmptyDefaults() {

        QueryParameters query = new QueryStringDefaults().builder().query("limit=50&offset=10").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(50, query.getLimit().longValue());
        Assert.assertEquals(10, query.getOffset().longValue());

        query = new QueryStringDefaults().builder().query("limit=110&offset=20").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(100, query.getLimit().longValue());
        Assert.assertEquals(20, query.getOffset().longValue());
    }

    @Test
    public void testDefaultLimit() {

        QueryParameters query = new QueryStringDefaults().defaultLimit(60).builder().query("").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(60, query.getLimit().longValue());
    }

    @Test
    public void testMaxLimit() {

        QueryParameters query = new QueryStringDefaults().maxLimit(60).builder().query("limit=200").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(60, query.getLimit().longValue());
    }

    @Test
    public void testDefaultOffset() {

        QueryParameters query = new QueryStringDefaults().defaultOffset(20).builder().query("").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(20, query.getOffset().longValue());
    }

    @Test
    public void testMultipleDefaults() {

        QueryParameters query = new QueryStringDefaults().defaultLimit(200).defaultOffset(20)
                .builder().query("").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(200, query.getLimit().longValue());
        Assert.assertEquals(20, query.getOffset().longValue());
    }

    @Test
    public void testEnabledPagination() {

        QueryParameters query = new QueryStringDefaults().enablePagination(true).builder()
                .query("limit=100&offset=200").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(100, query.getLimit().longValue());
        Assert.assertEquals(200, query.getOffset().longValue());

        query = new QueryStringDefaults().builder()
                .query("limit=100&offset=200").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(100, query.getLimit().longValue());
        Assert.assertEquals(200, query.getOffset().longValue());
    }

    @Test
    public void testDisabledPagination() {

        QueryParameters query = new QueryStringDefaults().enablePagination(false).builder()
                .query("limit=100&offset=200").build();

        Assert.assertNotNull(query);
        Assert.assertNull(query.getLimit());
        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testEnabledFilters() {

        QueryParameters query = new QueryStringDefaults().enableFilters(true).builder()
                .query("where=name:eq:tilen").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());

        Assert.assertNotNull(query.getFilters().get(0));
        Assert.assertEquals("name", query.getFilters().get(0).getField());
        Assert.assertEquals(FilterOperation.EQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("tilen", query.getFilters().get(0).getValue());

        query = new QueryStringDefaults().builder()
                .query("where=name:eq:tilen").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());

        Assert.assertNotNull(query.getFilters().get(0));
        Assert.assertEquals("name", query.getFilters().get(0).getField());
        Assert.assertEquals(FilterOperation.EQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("tilen", query.getFilters().get(0).getValue());
    }

    @Test
    public void testDisabledFilters() {

        QueryParameters query = new QueryStringDefaults().enableFilters(false).builder()
                .query("where=name:eq:tilen").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(0, query.getFilters().size());
    }

    @Test
    public void testEnabledOrder() {

        QueryParameters query = new QueryStringDefaults().enableOrder(true).builder()
                .query("order=name ASC").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());

        Assert.assertNotNull(query.getOrder().get(0));
        Assert.assertEquals("name", query.getOrder().get(0).getField());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());

        query = new QueryStringDefaults().builder()
                .query("order=name ASC").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());

        Assert.assertNotNull(query.getOrder().get(0));
        Assert.assertEquals("name", query.getOrder().get(0).getField());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());
    }

    @Test
    public void testDisabledOrder() {

        QueryParameters query = new QueryStringDefaults().enableOrder(false).builder()
                .query("order=name ASC").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(0, query.getOrder().size());
    }

    @Test
    public void testEnabledFields() {

        QueryParameters query = new QueryStringDefaults().enableFields(true).builder()
                .query("fields=name,email").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(2, query.getFields().size());

        Assert.assertEquals("name", query.getFields().get(0));
        Assert.assertEquals("email", query.getFields().get(1));

        query = new QueryStringDefaults().builder()
                .query("fields=name,email").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(2, query.getFields().size());

        Assert.assertEquals("name", query.getFields().get(0));
        Assert.assertEquals("email", query.getFields().get(1));
    }

    @Test
    public void testDisabledFields() {

        QueryParameters query = new QueryStringDefaults().enableFields(false).builder()
                .query("fields=name,email").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testFilterPredicate() {

        Set<String> allowedFilterFields = new HashSet<>();
        allowedFilterFields.add("email");

        QueryParameters query = new QueryStringDefaults()
                .allowFilter(f -> allowedFilterFields.contains(f.getField())).builder()
                .query("filter=username:eq:test email:isnull")
                .build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("email", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.ISNULL, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
    }

    @Test
    public void testOrderPredicate() {

        Set<String> allowedOrderFields = new HashSet<>();
        allowedOrderFields.add("email");

        QueryParameters query = new QueryStringDefaults()
                .allowOrder(f -> allowedOrderFields.contains(f.getField())).builder()
                .query("order=username DESC,email ASC")
                .build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());
        Assert.assertEquals("email", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());
    }

    @Test
    public void testFieldPredicate() {

        Set<String> allowedFields = new HashSet<>();
        allowedFields.add("email");

        QueryParameters query = new QueryStringDefaults().allowField(allowedFields::contains).builder()
                .query("fields=username,email")
                .build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("email", query.getFields().get(0));
    }
}
