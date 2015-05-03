package com.github.tfaga.lynx.test.jpa;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.User;
import com.github.tfaga.lynx.test.rules.JpaRule;
import com.github.tfaga.lynx.utils.JPAUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;
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
        Assert.assertEquals(1, users.size());
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
        Assert.assertEquals(47, users.size());
        Assert.assertEquals(1, users.get(0).getRole().intValue());
    }

    @Test
    public void testSingleDateFilter() {

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        QueryFilter qf = new QueryFilter();
        qf.setField("createdAt");
        qf.setOperation(FilterOperation.GT);
        qf.setDateValue(d);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(39, users.size());
    }

    @Test
    public void testWrongDateField() {

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        QueryFilter qf = new QueryFilter();
        qf.setField("lastname");
        qf.setOperation(FilterOperation.LTE);
        qf.setDateValue(d);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(0, users.size());
    }

    @Test
    public void testNonExistingField() {

        QueryFilter qf = new QueryFilter();
        qf.setField("asdas");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        try {
            JPAUtils.queryEntities(em, User.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("asdas", e.getField());
        }
    }

    @Test
    public void testNullValue() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.EQ);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
    }

    @Test
    public void testNullField() {

        QueryFilter qf = new QueryFilter();
        qf.setOperation(FilterOperation.NEQ);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        try {
            JPAUtils.queryEntities(em, User.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals(null, e.getField());
        }
    }
}
