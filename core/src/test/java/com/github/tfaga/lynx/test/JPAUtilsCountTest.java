package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.test.entities.User;
import com.github.tfaga.lynx.test.rules.JpaRule;
import com.github.tfaga.lynx.utils.JPAUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * @author Tilen Faganel
 */
public class JPAUtilsCountTest {

    @ClassRule
    public static JpaRule server = new JpaRule();

    private EntityManager em = server.getEntityManager();

    @Test
    public void testEmptyQueryCount() {

        Long count = JPAUtils.queryEntitiesCount(em, User.class);

        Assert.assertNotNull(count);
        Assert.assertEquals((long)100, count.longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyQueryWrongMethodCount() {

        JPAUtils.queryEntitiesCount(em, User.class, null);
    }

    @Test
    public void testQueryCount() {

        QueryParameters q = new QueryParameters();

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Bruce");
        qf.getValues().add("Karen");
        qf.getValues().add("Sandra");
        qf.getValues().add("Laura");
        q.getFilters().add(qf);

        qf = new QueryFilter();
        qf.setField("country");
        qf.setOperation(FilterOperation.LIKE);
        qf.setValue("%ina");
        q.getFilters().add(qf);

        Long count = JPAUtils.queryEntitiesCount(em, User.class, q);

        Assert.assertNotNull(count);
        Assert.assertEquals((long)2, count.longValue());
    }
}
