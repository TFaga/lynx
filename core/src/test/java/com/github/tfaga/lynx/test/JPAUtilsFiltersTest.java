package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.Project;
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
import javax.persistence.PersistenceException;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
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
    public void testMultipleFilters() {

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

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());
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

    @Test
    public void testInFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Bruce");
        qf.getValues().add("Karen");
        qf.getValues().add("Sandra");
        qf.getValues().add("Laura");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(9, users.size());
    }

    @Test
    public void testNinFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.NIN);
        qf.getValues().add("Bruce");
        qf.getValues().add("Karen");
        qf.getValues().add("Sandra");
        qf.getValues().add("Laura");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(91, users.size());
    }

    @Test
    public void testNotEqual() {

        QueryFilter qf = new QueryFilter();
        qf.setField("lastname");
        qf.setOperation(FilterOperation.NEQ);
        qf.setValue("Willis");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(97, users.size());
    }

    @Test
    public void testGte() {

        QueryFilter qf = new QueryFilter();
        qf.setField("role");
        qf.setOperation(FilterOperation.GTE);
        qf.setValue("1");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(47, users.size());

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        qf = new QueryFilter();
        qf.setField("createdAt");
        qf.setOperation(FilterOperation.GTE);
        qf.setDateValue(d);

        q = new QueryParameters();
        q.getFilters().add(qf);

        users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(40, users.size());
    }

    @Test
    public void testLt() {

        QueryFilter qf = new QueryFilter();
        qf.setField("role");
        qf.setOperation(FilterOperation.LT);
        qf.setValue("1");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(53, users.size());

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        qf = new QueryFilter();
        qf.setField("createdAt");
        qf.setOperation(FilterOperation.LT);
        qf.setDateValue(d);

        q = new QueryParameters();
        q.getFilters().add(qf);

        users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(60, users.size());
    }

    @Test
    public void testLte() {

        QueryFilter qf = new QueryFilter();
        qf.setField("role");
        qf.setOperation(FilterOperation.LTE);
        qf.setValue("0");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(53, users.size());

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        qf = new QueryFilter();
        qf.setField("createdAt");
        qf.setOperation(FilterOperation.LTE);
        qf.setDateValue(d);

        q = new QueryParameters();
        q.getFilters().add(qf);

        users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(61, users.size());
    }

    @Test
    public void testEqic() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("jULIa");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void testNeqic() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.NEQIC);
        qf.setValue("JaCK");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(98, users.size());
    }

    @Test
    public void testLikeic() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.LIKEIC);
        qf.setValue("jA%");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(5, users.size());
    }

    @Test
    public void testIntegerEq() {

        QueryFilter qf = new QueryFilter();
        qf.setField("role");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("0");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(53, users.size());
    }

    @Test
    public void testInic() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("sArAH");
        qf.getValues().add("ricHArd");
        qf.getValues().add("jACk");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(4, users.size());
    }

    @Test
    public void testNinic() {

        QueryFilter qf = new QueryFilter();
        qf.setField("firstname");
        qf.setOperation(FilterOperation.NINIC);
        qf.getValues().add("sArAH");
        qf.getValues().add("ricHArd");
        qf.getValues().add("jACk");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(96, users.size());
    }

    @Test
    public void testManyToOneRelation() {

        QueryFilter qf = new QueryFilter();
        qf.setField("user.id");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("28");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<Project> projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(5, projects.size());

        qf = new QueryFilter();
        qf.setField("user.firstname");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("sArAH");
        qf.getValues().add("ricHArd");
        qf.getValues().add("jACk");

        q = new QueryParameters();
        q.getFilters().add(qf);

        projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(8, projects.size());
    }

    @Test(expected = PersistenceException.class)
    public void testManyToOneRelationOnlyField() {

        QueryFilter qf = new QueryFilter();
        qf.setField("user");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("28");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, Project.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testOneToManyRelation() {

        QueryFilter qf = new QueryFilter();
        qf.setField("projects.id");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("10");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<User> users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());

        qf = new QueryFilter();
        qf.setField("projects.name");
        qf.setOperation(FilterOperation.NIN);
        qf.getValues().add("Green");
        qf.getValues().add("Violet");

        q = new QueryParameters();
        q.getFilters().add(qf);

        users = JPAUtils.queryEntities(em, User.class, q);

        Assert.assertNotNull(users);
        Assert.assertEquals(89, users.size());
    }

    @Test(expected = PersistenceException.class)
    public void testOneToManyRelationOnlyFieldInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("projects");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("28");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, User.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testNullFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("description");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("null");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<Project> projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(1, projects.size());
    }

    @Test
    public void testEnumFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("status");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("ACTIVE");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<Project> projects = JPAUtils.queryEntities(em, Project.class, q);

        Assert.assertNotNull(projects);
        Assert.assertEquals(50, projects.size());
    }
}
