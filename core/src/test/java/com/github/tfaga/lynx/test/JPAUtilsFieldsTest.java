package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.User;
import com.github.tfaga.lynx.test.utils.JpaUtil;
import com.github.tfaga.lynx.utils.JPAUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class JPAUtilsFieldsTest {

    @Parameterized.Parameters
    public static Collection<EntityManager> data() {

        JpaUtil jpaUtil = JpaUtil.getInstance();

        return Arrays.asList(
                jpaUtil.getEclipselinkEntityManager(),
                jpaUtil.getHibernateEntityManager()
        );
    }

    @Parameterized.Parameter
    public EntityManager em;

    @Test
    public void testSingleField() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("firstname");

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getFirstname());
        Assert.assertNull(users.get(0).getLastname());
        Assert.assertEquals("Jason", users.get(0).getFirstname());
    }

    @Test
    public void testMultipleFields() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("firstname");
        q.getFields().add("email");
        q.getFields().add("role");

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getFirstname());
        Assert.assertNotNull(users.get(0).getEmail());
        Assert.assertNotNull(users.get(0).getRole());
        Assert.assertNull(users.get(0).getLastname());
        Assert.assertEquals("Jason", users.get(0).getFirstname());
        Assert.assertEquals("jramos0@ox.ac.uk", users.get(0).getEmail());
        Assert.assertEquals(0, users.get(0).getRole().intValue());
    }

    @Test
    public void testIdAvailable() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("lastname");

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getLastname());
        Assert.assertNotNull(users.get(0).getId());
        Assert.assertNull(users.get(0).getFirstname());
        Assert.assertEquals("Ramos", users.get(0).getLastname());
        Assert.assertEquals(1, users.get(0).getId().intValue());
    }

    @Test
    public void testIdParameter() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("id");

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getId());
        Assert.assertNull(users.get(0).getFirstname());
        Assert.assertNotNull(users.get(99).getId());
        Assert.assertNull(users.get(99).getFirstname());
    }

    // Currently unsupported
    @Test(expected = NoSuchEntityFieldException.class)
    public void testEmbeddedField() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("address.country");

        JPAUtils.queryEntities(em, User.class, q);
    }

    @Test(expected = NoSuchEntityFieldException.class)
    public void testNonExistingField() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("something");

        JPAUtils.queryEntities(em, User.class, q);
    }
}
