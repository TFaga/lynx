package com.github.tfaga.lynx.test.jpa;

import com.github.tfaga.lynx.beans.QueryParameters;
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
public class JPAUtilsPagingTest {

    @ClassRule
    public static JpaRule server = new JpaRule();

    private EntityManager em = server.getEntityManager();

    @Test
    public void testEmptyQuery() {

        List<User> users = JPAUtils.queryEntities(em, User.class, new QueryParameters());

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullQueryWrongMethod() {

        JPAUtils.queryEntities(em, User.class, null);
    }

    @Test
    public void testNullQuery() {

        List<User> users = JPAUtils.queryEntities(em, User.class);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 100);
    }

    @Test
    public void testLimit() {

        QueryParameters q = new QueryParameters();
        q.setLimit(10);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 10);
        Assert.assertNotNull(users.get(0).getId());
        Assert.assertEquals(users.get(0).getId().intValue(), 1);
        Assert.assertNotNull(users.get(9).getId());
        Assert.assertEquals(users.get(9).getId().intValue(), 10);
    }

    @Test
    public void testOffset() {

        QueryParameters q = new QueryParameters();
        q.setOffset(30);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 70);
        Assert.assertNotNull(users.get(0).getId());
        Assert.assertEquals(users.get(0).getId().intValue(), 31);
        Assert.assertNotNull(users.get(69).getId());
        Assert.assertEquals(users.get(69).getId().intValue(), 100);
    }

    @Test
    public void testLimitWithOffset() {

        QueryParameters q = new QueryParameters();
        q.setLimit(25);
        q.setOffset(40);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 25);
        Assert.assertNotNull(users.get(0).getId());
        Assert.assertEquals(users.get(0).getId().intValue(), 41);
        Assert.assertNotNull(users.get(24).getId());
        Assert.assertEquals(users.get(24).getId().intValue(), 65);
    }

    @Test
    public void testLimitTooBig() {

        QueryParameters q = new QueryParameters();
        q.setLimit(300);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 100);
    }

    @Test
    public void testOffsetOutOfBounds() {

        QueryParameters q = new QueryParameters();
        q.setOffset(200);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 0);
    }

    @Test
    public void testFindEntity() {

        User u = JPAUtils.getEntity(em, User.class, 1);

        Assert.assertNotNull(u);
    }
}
