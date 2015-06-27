package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.QueryFormatError;
import com.github.tfaga.lynx.exceptions.QueryFormatException;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryStringBuilderFiltersTest {

    @Test
    public void testQueryFieldsObject() {

        QueryParameters query = new QueryParameters();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testSingleFilter() {

        QueryParameters query = QueryParameters.query("filter=username:eq:test").build();

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

        QueryParameters query = QueryParameters.query("filter=username:eq:'test test'").build();

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

        QueryParameters query = QueryParameters.query("where=username:eq:'test test' lastname:gte:gale").build();

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
    public void testMalformedUniaryFilter() {

        try {

            QueryParameters.query("filter=usernameeq:test test").build();
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("filter", e.getField());
            Assert.assertNotNull(e.getReason());
            Assert.assertEquals(QueryFormatError.NO_SUCH_CONSTANT, e.getReason());
        }
    }

    @Test
    public void testMalformedBinaryFilter() {

        QueryParameters query = QueryParameters.query("filter=usernameeq:eq test").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(0, query.getFilters().size());
    }

    @Test
    public void testUnsupportedOperationFilter() {

        try {

            QueryParameters.query("filter=username:equal:test test").build();
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("filter", e.getField());
            Assert.assertNotNull(e.getReason());
            Assert.assertEquals(QueryFormatError.NO_SUCH_CONSTANT, e.getReason());
        }
    }

    @Test
    public void testEmptyFilter() {

        QueryParameters query = QueryParameters.query("filter=").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(0, query.getFilters().size());
    }

    @Test
    public void testMultipleKeyFilters() {

        QueryParameters query = QueryParameters.query("where=username:eq:'test test' " +
                "lastname:gte:gale&filter=country:neq:SI").build();

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

        QueryParameters query = QueryParameters.query("where=username:in:[johnf,garryz]").build();

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

        QueryParameters query = QueryParameters.query("where=username:in:[johnf,,,,garryz]").build();

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

        QueryParameters query = QueryParameters.query("where=username:neq:[johnf,garryz]").build();

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

        QueryParameters query = QueryParameters.query("where=username:gte:dt'2014-11-26T11:15:08Z'").build();

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
            QueryParameters.query("where=username:gte:dt'2014-11-26T1sdf1:15:08Z'").build();
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("where", e.getField());
            Assert.assertNotNull(e.getReason());
            Assert.assertEquals(QueryFormatError.MALFORMED, e.getReason());
        }
    }

    @Test
    public void testNoDateIdentifier() {

        QueryParameters query = QueryParameters.query("where=username:gte:'2014-11-26T11:15:08Z'").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("username", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.GTE, query.getFilters().get(0).getOperation());
        Assert.assertEquals("2014-11-26T11:15:08Z", query.getFilters().get(0).getValue());
    }

    @Test
    public void testQueryDecoded() {

        QueryParameters query = QueryParameters.query("where=firstname:like:Kar%").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("firstname", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.LIKE, query.getFilters().get(0).getOperation());
        Assert.assertEquals("Kar%", query.getFilters().get(0).getValue());
    }

    @Test
    public void testQueryEncoded() {

        QueryParameters query = QueryParameters.queryEncoded("where=firstname:like:Kar%25").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("firstname", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.LIKE, query.getFilters().get(0).getOperation());
        Assert.assertEquals("Kar%", query.getFilters().get(0).getValue());
    }

    @Test
    public void testUriDecoded() {

        QueryParameters query = QueryParameters.uriEncoded("api.github" +
                ".com/tfaga/repos?where=firstname:like:Kar%25%20").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("firstname", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.LIKE, query.getFilters().get(0).getOperation());
        Assert.assertEquals("Kar%", query.getFilters().get(0).getValue());
    }

    @Test
    public void testQuotesInInFilter() {

        QueryParameters query = QueryParameters.query("where=country:in:['Czech Republic',China]").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("country", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.IN, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
        Assert.assertEquals(2, query.getFilters().get(0).getValues().size());
        Assert.assertEquals("Czech Republic", query.getFilters().get(0).getValues().get(0));
        Assert.assertEquals("China", query.getFilters().get(0).getValues().get(1));
    }

    @Test
    public void testQuotesInNinFilter() {

        QueryParameters query = QueryParameters.query("where=country:nin:['Czech Republic',Nigeria]").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("country", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.NIN, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
        Assert.assertEquals(2, query.getFilters().get(0).getValues().size());
        Assert.assertEquals("Czech Republic", query.getFilters().get(0).getValues().get(0));
        Assert.assertEquals("Nigeria", query.getFilters().get(0).getValues().get(1));
    }

    @Test
    public void testIsNullFilter() {

        QueryParameters query = QueryParameters.query("where=description:isnull").build();

        Assert.assertNotNull(query);
        Assert.assertNotNull(query.getFilters());
        Assert.assertEquals(1, query.getFilters().size());
        Assert.assertEquals("description", query.getFilters().get(0).getField());
        Assert.assertNotNull(query.getFilters().get(0).getOperation());
        Assert.assertEquals(FilterOperation.ISNULL, query.getFilters().get(0).getOperation());
        Assert.assertNull(query.getFilters().get(0).getValue());
    }
}
