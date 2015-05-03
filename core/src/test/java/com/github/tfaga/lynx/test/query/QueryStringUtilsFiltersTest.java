package com.github.tfaga.lynx.test.query;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.QueryFormatError;
import com.github.tfaga.lynx.exceptions.QueryFormatException;
import com.github.tfaga.lynx.utils.QueryStringUtils;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Tilen Faganel
 */
public class QueryStringUtilsFiltersTest {

    @Test
    public void testQueryFieldsObject() {

        QueryParameters query = new QueryParameters();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testSingleFilter() {

        QueryParameters query = QueryStringUtils.parse("filter=username:eq:test");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.EQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("test", query.getFilters().get(0).getValue());
    }

    @Test
    public void testSingleFilterWithQuotes() {

        QueryParameters query = QueryStringUtils.parse("filter=username:eq:'test test'");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.EQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("test test", query.getFilters().get(0).getValue());
    }

    @Test
    public void testMultipleFilters() {

        QueryParameters query = QueryStringUtils.parse("where=username:eq:'test test' lastname:gte:gale");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(2, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.EQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("test test", query.getFilters().get(0).getValue());
        Assert.assertEquals("lastname", query.getFilters().get(1).getField());
        Assert.assertNotNull(query.getFilters().get(1).getOperation());
        Assert.assertEquals(FilterOperation.GTE, query.getFilters().get(1).getOperation());
        Assert.assertEquals("gale", query.getFilters().get(1).getValue());
    }

    @Test
    public void testMalformedFilter() {

        QueryParameters query = QueryStringUtils.parse("filter=usernameeq:test test");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(0, query.getFilters().size());
    }

    @Test
    public void testUnsupportedOperationFilter() {

        try {

            QueryStringUtils.parse("filter=username:equal:test test");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("filter", e.getField());
            Assert.assertNotNull(e.getReason());
            Assert.assertEquals(QueryFormatError.NO_SUCH_CONSTANT, e.getReason());
        }
    }

    @Test
    public void testEmptyFilter() {

        QueryParameters query = QueryStringUtils.parse("filter=");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(0, query.getFilters().size());
    }

    @Test
    public void testMultipleKeyFilters() {

        QueryParameters query = QueryStringUtils.parse("where=username:eq:'test test' lastname:gte:gale&filter=country:neq:SI");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("country", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.NEQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("SI", query.getFilters().get(0).getValue());
    }

    @Test
    public void testInFilter() {

        QueryParameters query = QueryStringUtils.parse("where=username:in:[johnf,garryz]");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.IN, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
        Assert.assertEquals(2, query.getFilters().get(0).getValues().size());
        Assert.assertEquals("johnf", query.getFilters().get(0).getValues().get(0));
        Assert.assertEquals("garryz", query.getFilters().get(0).getValues().get(1));
    }

    @Test
    public void testInFilterEmptyElements() {

        QueryParameters query = QueryStringUtils.parse("where=username:in:[johnf,,,,garryz]");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.IN, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
        Assert.assertEquals(2, query.getFilters().get(0).getValues().size());
        Assert.assertEquals("johnf", query.getFilters().get(0).getValues().get(0));
        Assert.assertEquals("garryz", query.getFilters().get(0).getValues().get(1));
    }

    @Test
    public void testArrayValueWhenNotInOperation() {

        QueryParameters query = QueryStringUtils.parse("where=username:neq:[johnf,garryz]");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.NEQ, query.getFilters().get(0).getOperation());
        Assert.assertEquals("[johnf,garryz]", query.getFilters().get(0).getValue());
    }

    @Test
    public void testDateValueFilter() {

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        QueryParameters query = QueryStringUtils.parse("where=username:gte:dt'2014-11-26T11:15:08Z'");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.GTE, query.getFilters().get(0).getOperation());
        Assert.assertEquals(d, query.getFilters().get(0).getDateValue());
    }

    @Test
    public void testMalformedDateFilter() {

        try {
            QueryStringUtils.parse("where=username:gte:dt'2014-11-26T1sdf1:15:08Z'");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("where", e.getField());
            Assert.assertNotNull(e.getReason());
            Assert.assertEquals(QueryFormatError.MALFORMED, e.getReason());
        }
    }

    @Test
    public void testNoDateIdentifier() {

        QueryParameters query = QueryStringUtils.parse("where=username:gte:'2014-11-26T11:15:08Z'");

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.GTE, query.getFilters().get(0).getOperation());
        Assert.assertEquals("2014-11-26T11:15:08Z", query.getFilters().get(0).getValue());
    }
}
