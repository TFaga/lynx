package com.github.tfaga.lynx.test.query;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.enums.QueryFormatError;
import com.github.tfaga.lynx.exceptions.QueryFormatException;
import com.github.tfaga.lynx.utils.QueryStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tilen Faganel
 */
public class QueryStringUtilsOrderTest {

    @Test
    public void testQueryOrderObject() {

        QueryParameters query = new QueryParameters();

        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(0, query.getOrder().size());
    }

    @Test
    public void testSingleOrder() {

        QueryParameters query = QueryStringUtils.parse("$order=username DESC");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());
        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.DESC, query.getOrder().get(0).getOrder());
    }

    @Test
    public void testOrderWithoutDirection() {

        QueryParameters query = QueryStringUtils.parse("$sort=username");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());
        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());
    }

    @Test
    public void testEmptyOrder() {

        QueryParameters query = QueryStringUtils.parse("$order=");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(0, query.getOrder().size());
    }

    @Test
    public void testNoOrderField() {

        try {

            QueryStringUtils.parse("$order= ASC");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("$order", e.getField());
            Assert.assertEquals(QueryFormatError.MALFORMED, e.getReason());
        }
    }

    @Test
    public void testMalformedDirection() {

        try {

            QueryStringUtils.parse("$sort=lastname SOMETHING");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("$sort", e.getField());
            Assert.assertEquals(QueryFormatError.MALFORMED, e.getReason());
        }
    }

    @Test
    public void testMultipleOrders() {

        QueryParameters query = QueryStringUtils.parse("$order=username ASC,lastname DESC");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(2, query.getOrder().size());

        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());

        Assert.assertEquals("lastname", query.getOrder().get(1).getField());
        Assert.assertNotNull(query.getOrder().get(1).getOrder());
        Assert.assertEquals(OrderDirection.DESC, query.getOrder().get(1).getOrder());
    }

    @Test
    public void testMultipleOrdersWithoutDirections() {

        QueryParameters query = QueryStringUtils.parse("$order=username,lastname DESC,firstname");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(3, query.getOrder().size());

        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());

        Assert.assertEquals("lastname", query.getOrder().get(1).getField());
        Assert.assertNotNull(query.getOrder().get(1).getOrder());
        Assert.assertEquals(OrderDirection.DESC, query.getOrder().get(1).getOrder());

        Assert.assertEquals("firstname", query.getOrder().get(2).getField());
        Assert.assertNotNull(query.getOrder().get(2).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(2).getOrder());
    }

    @Test
    public void testMultipleOrdersWithEmptyBetween() {

        QueryParameters query = QueryStringUtils.parse("$order=username,,,,firstname DESC");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(2, query.getOrder().size());

        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());

        Assert.assertEquals("firstname", query.getOrder().get(1).getField());
        Assert.assertNotNull(query.getOrder().get(1).getOrder());
        Assert.assertEquals(OrderDirection.DESC, query.getOrder().get(1).getOrder());
    }

    @Test
    public void testMultipleOrdersMalformed() {

        try {

            QueryStringUtils.parse("$order=username, firstname DESC");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("$order", e.getField());
            Assert.assertEquals(QueryFormatError.MALFORMED, e.getReason());
        }
    }

    @Test
    public void testMultipleOrderKeys() {

        QueryParameters query = QueryStringUtils.parse("$order=username,firstname&$sort=lastname DESC");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(1, query.getOrder().size());

        Assert.assertEquals("lastname", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.DESC, query.getOrder().get(0).getOrder());
    }

    @Test
    public void testMultipleOrderRepeatFields() {

        QueryParameters query = QueryStringUtils.parse("$order=username,firstname,username DESC");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getOrder());
        Assert.assertEquals(2, query.getOrder().size());

        Assert.assertEquals("username", query.getOrder().get(0).getField());
        Assert.assertNotNull(query.getOrder().get(0).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(0).getOrder());

        Assert.assertEquals("firstname", query.getOrder().get(1).getField());
        Assert.assertNotNull(query.getOrder().get(1).getOrder());
        Assert.assertEquals(OrderDirection.ASC, query.getOrder().get(1).getOrder());
    }

}
