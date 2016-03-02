package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.utils.QueryStringBuilder;
import com.github.tfaga.lynx.utils.QueryStringDefaults;
import org.junit.Assert;
import org.junit.Test;

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
}
