package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class QueryStringBuilderFieldsTest {

    @Test
    public void testQueryFieldsObject() {

        QueryParameters query = new QueryParameters();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testSingleField() {

        QueryParameters query = QueryParameters.query("fields=username").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
    }

    @Test
    public void testMultipleFields() {

        QueryParameters query = QueryParameters.query("fields=username,firstname,lastname").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(3, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
        Assert.assertEquals("lastname", query.getFields().get(2));
    }

    @Test
    public void testEmptyFields() {

        QueryParameters query = QueryParameters.query("select=username,,,,firstname,lastname")
                .build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(3, query.getFields().size());
        Assert.assertEquals("username", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
        Assert.assertEquals("lastname", query.getFields().get(2));
    }

    @Test
    public void testEmptyField() {

        QueryParameters query = QueryParameters.query("fields=").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testEmptyFieldWithoutDelimiter() {

        QueryParameters query = QueryParameters.query("fields").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(0, query.getFields().size());
    }

    @Test
    public void testDuplicateFields() {

        QueryParameters query = QueryParameters.query("select=country,firstname,country").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(2, query.getFields().size());
        Assert.assertEquals("country", query.getFields().get(0));
        Assert.assertEquals("firstname", query.getFields().get(1));
    }

    @Test
    public void testMultipleFieldKeys() {

        QueryParameters query = QueryParameters.query("select=username,firstname," +
                "lastname&fields=address").build();

        Assert.assertNotNull(query.getFields());
        Assert.assertEquals(1, query.getFields().size());
        Assert.assertEquals("address", query.getFields().get(0));
    }
}
