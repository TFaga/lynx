package com.github.tfaga.lynx.test.query;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.exceptions.QueryFormatException;
import com.github.tfaga.lynx.utils.QueryStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tilen Faganel
 */
public class QueryStringUtilsParseTest {

    @Test
    public void testEmpty() {

        QueryParameters query = QueryStringUtils.parse("");

        Assert.assertNotNull(query);
        Assert.assertNull(query.getLimit());
        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testNull() {

        QueryParameters query = QueryStringUtils.parse(null);

        Assert.assertNotNull(query);
        Assert.assertNull(query.getLimit());
        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testLimit() {

        QueryParameters query = QueryStringUtils.parse("$limit=123");

        Assert.assertEquals(query.getLimit().longValue(), 123);

        query = QueryStringUtils.parse("$max=321");

        Assert.assertEquals(query.getLimit().longValue(), 321);
    }

    @Test
    public void testMultipleLimits() {

        QueryParameters query = QueryStringUtils.parse("$limit=123&$limit=111&$max=322");

        Assert.assertEquals(query.getLimit().longValue(), 322);
    }

    @Test
    public void testWrongLimitFormat() {

        try {

            QueryStringUtils.parse("$max=122&$limit=asd");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals(e.getField(), "$limit");
        }

        try {

            QueryStringUtils.parse("$max=&$limit=222");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals(e.getField(), "$max");
        }
    }
}
