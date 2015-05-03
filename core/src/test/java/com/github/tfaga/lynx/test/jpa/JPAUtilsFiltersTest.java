package com.github.tfaga.lynx.test.jpa;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.test.entities.User;
import com.github.tfaga.lynx.test.rules.JpaRule;
import com.github.tfaga.lynx.utils.JPAUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * @author Tilen Faganel
 */
public class JPAUtilsFiltersTest {

    @ClassRule
    public static JpaRule server = new JpaRule();

    private EntityManager em = server.getEntityManager();

    @Test
    public void testSingleFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("Sandra");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 1);
        Assert.assertNotNull(users.get(0).getFirstname());
        Assert.assertEquals("Sandra", users.get(0).getFirstname());
    }

    @Test
    public void testSingleIntegerFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("role");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("0");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 47);
        Assert.assertEquals(1, users.get(0).getRole().intValue());
    }
}
