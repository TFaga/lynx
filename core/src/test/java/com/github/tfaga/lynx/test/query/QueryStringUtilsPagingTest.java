package com.github.tfaga.lynx.test.query;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.QueryFormatError;
import com.github.tfaga.lynx.exceptions.QueryFormatException;
import com.github.tfaga.lynx.utils.QueryStringUtils;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Tilen Faganel
 */
public class QueryStringUtilsPagingTest {

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

        QueryParameters query = QueryStringUtils.parse("limit=123");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(123, query.getLimit().longValue());

        query = QueryStringUtils.parse("max=321");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(321, query.getLimit().longValue());
    }

    @Test
    public void testMultipleLimits() {

        QueryParameters query = QueryStringUtils.parse("limit=123&limit=111&max=322");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(322, query.getLimit().longValue());

        query = QueryStringUtils.parse("max=981&max=682");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(682, query.getLimit().longValue());
    }

    @Test
    public void testNegativeLimit() {

        try {

            QueryStringUtils.parse("limit=-123");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("limit", e.getField());
            Assert.assertEquals(QueryFormatError.NEGATIVE, e.getReason());
        }
    }

    @Test
    public void testWrongLimitFormat() {

        try {

            QueryStringUtils.parse("max=122&limit=asd");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("limit", e.getField());
            Assert.assertEquals(QueryFormatError.NOT_A_NUMBER, e.getReason());
        }
    }

    @Test
    public void testEmptyLimit() {

        QueryParameters query = QueryStringUtils.parse("limit=");

        Assert.assertNotNull(query);
        Assert.assertNull(query.getLimit());
    }

    @Test
    public void testLimitUri() {

        QueryParameters query = QueryStringUtils.parseUri("api.github.com/tfaga/repos?limit=123");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(123, query.getLimit().longValue());
    }

    @Test
    public void testLimitUriWithFragment() {

        QueryParameters query = QueryStringUtils.parseUri("api.github.com/tfaga/repos?limit=123#header1");

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(123, query.getLimit().longValue());

        query = QueryStringUtils.parseUri("api.github.com/tfaga/repos#header2?skip=98172");

        Assert.assertNull(query.getLimit());
    }

    @Test
    public void testLimitUriObject() throws URISyntaxException {

        URI uri = new URI("api.github.com/tfaga/repos?max=9186");

        QueryParameters query = QueryStringUtils.parseUri(uri);

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(9186, query.getLimit().longValue());
    }

    @Test
    public void testLimitUriObjectWithFragment() throws URISyntaxException {

        URI uri = new URI("api.github.com/tfaga/repos?max=9186#header3");

        QueryParameters query = QueryStringUtils.parseUri(uri);

        Assert.assertNotNull(query.getLimit());
        Assert.assertEquals(9186, query.getLimit().longValue());

        uri = new URI("api.github.com/tfaga/repos#header4?max=9186");

        query = QueryStringUtils.parseUri(uri);

        Assert.assertNull(query.getLimit());
    }

    @Test
    public void testOffset() {

        QueryParameters query = QueryStringUtils.parse("offset=921");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(921, query.getOffset().longValue());

        query = QueryStringUtils.parse("skip=824");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(824, query.getOffset().longValue());
    }

    @Test
    public void testMultipleOffsets() {

        QueryParameters query = QueryStringUtils.parse("skip=2199&offset=95461&skip=411");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(411, query.getOffset().longValue());

        query = QueryStringUtils.parse("offset=9881&offset=871263");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(871263, query.getOffset().longValue());
    }

    @Test
    public void testNegativeOffset() {

        try {

            QueryStringUtils.parse("offset=-123");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("offset", e.getField());
            Assert.assertEquals(QueryFormatError.NEGATIVE, e.getReason());
        }
    }

    @Test
    public void testWrongOffsetFormat() {

        try {

            QueryStringUtils.parse("skip=122&skip=asd");
            Assert.fail("No exception was thrown");
        } catch (QueryFormatException e) {

            Assert.assertEquals("skip", e.getField());
            Assert.assertEquals(QueryFormatError.NOT_A_NUMBER, e.getReason());
        }
    }

    @Test
    public void testEmptyOffset() {

        QueryParameters query = QueryStringUtils.parse("skip=");

        Assert.assertNotNull(query);
        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testOffsetUri() {

        QueryParameters query = QueryStringUtils.parseUri("api.github.com/tfaga/repos?skip=98172");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(98172, query.getOffset().longValue());
    }

    @Test
    public void testOffsetUriWithFragment() {

        QueryParameters query = QueryStringUtils.parseUri("api.github.com/tfaga/repos?skip=98172#id1");

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(98172, query.getOffset().longValue());

        query = QueryStringUtils.parseUri("api.github.com/tfaga/repos#id2?skip=98172");

        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testOffsetUriObject() throws URISyntaxException {

        URI uri = new URI("api.github.com/tfaga/repos?offset=12312");

        QueryParameters query = QueryStringUtils.parseUri(uri);

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(12312, query.getOffset().longValue());
    }

    @Test
    public void testOffsetUriObjectWithFragment() throws URISyntaxException {

        URI uri = new URI("api.github.com/tfaga/repos?offset=12312#id3");

        QueryParameters query = QueryStringUtils.parseUri(uri);

        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(12312, query.getOffset().longValue());

        uri = new URI("api.github.com/tfaga/repos#id4?offset=12312");

        query = QueryStringUtils.parseUri(uri);

        Assert.assertNull(query.getOffset());
    }

    @Test
    public void testOffsetWithLimit() {

        QueryParameters query = QueryStringUtils.parse("offset=123&limit=22");

        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(22, query.getLimit().longValue());
        Assert.assertEquals(123, query.getOffset().longValue());

        query = QueryStringUtils.parse("skip=123&max=22&skip=444");

        Assert.assertNotNull(query.getLimit());
        Assert.assertNotNull(query.getOffset());
        Assert.assertEquals(22, query.getLimit().longValue());
        Assert.assertEquals(444, query.getOffset().longValue());
    }
}
