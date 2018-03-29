package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.exceptions.InvalidEntityFieldException;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.Project;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class JPAUtilsOrderTest {

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
    public void testSingleOrder() {

        QueryOrder qo = new QueryOrder();
        qo.setField("firstname");
        qo.setOrder(OrderDirection.ASC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getFirstname());
        Assert.assertEquals("Amanda", users.get(0).getFirstname());
        Assert.assertNotNull(users.get(99).getFirstname());
        Assert.assertEquals("Victor", users.get(99).getFirstname());
    }

    @Test
    public void testSingleOrderDesc() {

        QueryOrder qo = new QueryOrder();
        qo.setField("lastname");
        qo.setOrder(OrderDirection.DESC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getLastname());
        Assert.assertEquals("Willis", users.get(0).getLastname());
        Assert.assertNotNull(users.get(99).getLastname());
        Assert.assertEquals("Austin", users.get(99).getLastname());
    }

    @Test
    public void testMultipleOrders() {

        QueryParameters q = new QueryParameters();

        QueryOrder qo = new QueryOrder();
        qo.setField("role");
        qo.setOrder(OrderDirection.DESC);
        q.getOrder().add(qo);

        qo = new QueryOrder();
        qo.setField("lastname");
        qo.setOrder(OrderDirection.ASC);
        q.getOrder().add(qo);

        qo = new QueryOrder();
        qo.setField("firstname");
        qo.setOrder(OrderDirection.DESC);
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getFirstname());
        Assert.assertNotNull(users.get(0).getLastname());
        Assert.assertEquals("Larry", users.get(0).getFirstname());
        Assert.assertEquals("Bailey", users.get(0).getLastname());
        Assert.assertNotNull(users.get(99).getFirstname());
        Assert.assertNotNull(users.get(99).getLastname());
        Assert.assertEquals("Bonnie", users.get(99).getFirstname());
        Assert.assertEquals("Willis", users.get(99).getLastname());
    }

    @Test
    public void testNullDirection() {

        QueryOrder qo = new QueryOrder();
        qo.setField("lastname");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getLastname());
        Assert.assertEquals("Austin", users.get(0).getLastname());
        Assert.assertNotNull(users.get(99).getLastname());
        Assert.assertEquals("Willis", users.get(99).getLastname());
    }

    @Test
    public void testNullField() {

        QueryOrder qo = new QueryOrder();

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q)
                .stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getLastname());
        Assert.assertEquals("Ramos", users.get(0).getLastname());
        Assert.assertNotNull(users.get(99).getLastname());
        Assert.assertEquals("Hall", users.get(99).getLastname());
    }

    @Test
    public void testNonExistentColumn() {

        QueryOrder qo = new QueryOrder();
        qo.setField("lstnm");
        qo.setOrder(OrderDirection.DESC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        try {

            JPAUtils.queryEntities(em, User.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("lstnm", e.getField());
        }
    }

    @Test
    public void testCaseSensitiveField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("firsTNAmE");
        qo.setOrder(OrderDirection.ASC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        try {

            JPAUtils.queryEntities(em, User.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("firsTNAmE", e.getField());
        }
    }

    @Test
    public void testManyToOne() {

        QueryOrder qo = new QueryOrder();
        qo.setField("user.firstname");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<Project> projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(100, projects.size());
        Assert.assertNotNull(projects.get(0).getName());
        Assert.assertEquals("Red", projects.get(0).getName());
        Assert.assertNotNull(projects.get(99).getName());
        Assert.assertEquals("Turquoise", projects.get(99).getName());
    }

    @Test
    public void testManyToOneOnlyField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("user");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<Project> projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(100, projects.size());
        Assert.assertNotNull(projects.get(0).getName());
        Assert.assertEquals("Goldenrod", projects.get(0).getName());
        Assert.assertNotNull(projects.get(99).getName());
        Assert.assertEquals("Yellow", projects.get(99).getName());
    }

    @Test
    public void testEmbedded() {

        QueryOrder qo = new QueryOrder();
        qo.setField("address.country");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(100, users.size());
        Assert.assertNotNull(users.get(0).getAddress());
        Assert.assertNotNull(users.get(0).getAddress().getCountry());
        Assert.assertEquals("Argentina", users.get(0).getAddress().getCountry());
        Assert.assertNotNull(users.get(99).getAddress());
        Assert.assertNotNull(users.get(99).getAddress().getCountry());
        Assert.assertEquals("Venezuela", users.get(99).getAddress().getCountry());
    }

    @Test(expected = InvalidEntityFieldException.class)
    public void testOneToMany() {

        QueryOrder qo = new QueryOrder();
        qo.setField("projects.name");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        JPAUtils.queryEntities(em, User.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test(expected = InvalidEntityFieldException.class)
    public void testOneToManyOnlyField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("projects");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        JPAUtils.queryEntities(em, User.class, q);
        Assert.fail("No exception was thrown");
    }
}
