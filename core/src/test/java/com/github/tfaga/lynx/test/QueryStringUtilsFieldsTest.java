package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.utils.QueryStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tilen Faganel
 */
public class QueryStringUtilsFieldsTest {

    @Test
    public void testQueryFieldsObject() {

        QueryParameters query = new QueryParameters();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testSingleField() {

        QueryParameters query = QueryStringUtils.parse("fields=username");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
    }

    @Test
    public void testMultipleFields() {

        QueryParameters query = QueryStringUtils.parse("fields=username,firstname,lastname");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(3, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
        Assert.assertEquals("lastname", query.getFields().get(2));
    }

    @Test
    public void testEmptyFields() {

        QueryParameters query = QueryStringUtils.parse("select=username,,,,firstname,lastname");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(3, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
        Assert.assertEquals("lastname", query.getFields().get(2));
    }

    @Test
    public void testEmptyField() {

        QueryParameters query = QueryStringUtils.parse("fields=");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testEmptyFieldWithoutDelimiter() {

        QueryParameters query = QueryStringUtils.parse("fields");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testDuplicateFields() {

        QueryParameters query = QueryStringUtils.parse("select=country,firstname,country");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(2, query.getFields().size());
        Assert.assertEquals("country", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
    }

    @Test
    public void testMultipleFieldKeys() {

        QueryParameters query = QueryStringUtils.parse("select=username,firstname,lastname&fields=address");

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("address", query.getFields().get(0));
    }
}
