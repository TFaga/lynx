package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.AccountEntity;
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
        q.getFields().add("name");

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertNull(accounts.get(0).getValue());
        Assert.assertEquals("Evvie", accounts.get(0).getName());
    }

    @Test
    public void testMultipleFields() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("name");
        q.getFields().add("value");

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(0).getValue());
        Assert.assertNull(accounts.get(0).getSize());
        Assert.assertEquals("Evvie", accounts.get(0).getName());
        Assert.assertEquals(34, accounts.get(0).getValue().intValue());
    }

    @Test
    public void testIdAvailable() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("name");

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(0).getId());
        Assert.assertNull(accounts.get(0).getValue());
        Assert.assertEquals("Evvie", accounts.get(0).getName());
        Assert.assertEquals(1, accounts.get(0).getId().intValue());
    }

    @Test
    public void testIdParameter() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("id");

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getId());
        Assert.assertNull(accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(49).getId());
        Assert.assertNull(accounts.get(49).getName());
    }

    // Currently unsupported
    @Test(expected = NoSuchEntityFieldException.class)
    public void testEmbeddedField() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("address.country");

        JPAUtils.queryEntities(em, AccountEntity.class, q);
    }

    @Test(expected = NoSuchEntityFieldException.class)
    public void testNonExistingField() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("something");

        JPAUtils.queryEntities(em, AccountEntity.class, q);
    }
}
