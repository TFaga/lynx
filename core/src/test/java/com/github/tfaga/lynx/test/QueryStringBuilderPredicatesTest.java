package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.OrderDirection;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
public class QueryStringBuilderPredicatesTest {

    @Test
    public void testFilterPredicate() {

        Set<String> allowedFilterFields = new HashSet<>();
        allowedFilterFields.add("email");

        QueryParameters query = QueryParameters.query("filter=username:eq:test email:isnull")
                .allowFilter(f -> allowedFilterFields.contains(f.getField())).build();

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

        QueryParameters query = QueryParameters.query("order=username DESC,email ASC")
                .allowOrder(f -> allowedOrderFields.contains(f.getField())).build();

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

        QueryParameters query = QueryParameters.query("fields=username,email")
                .allowField(allowedFields::contains).build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("email", query.getFields().get(0));
    }

    @Test
    public void testMultiplePredicates() {

        Set<String> allowedFields = new HashSet<>();
        allowedFields.add("email");

        QueryParameters query = QueryParameters
                .query("fields=username,email&order=username DESC,email&filter=username:eq:test email:isnull")
                .allowField(allowedFields::contains)
                .allowOrder(o -> allowedFields.contains(o.getField()))
                .allowFilter(f -> allowedFields.contains(f.getField())).build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("email", query.getFields().get(0));
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());
        Assert.assertEquals("email", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("email", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.ISNULL, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
    }
}
