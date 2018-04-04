package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
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
public class JPAUtilsPagingTest {

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
    public void testEmptyQuery() {

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, new QueryParameters());

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
    }

    @Test
    public void testNullQuery() {

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
    }

    @Test
    public void testLimit() {

        QueryParameters q = new QueryParameters();
        q.setLimit(10);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(10, accounts.size());
        Assert.assertNotNull(accounts.get(0).getId());
        Assert.assertEquals(1, accounts.get(0).getId().intValue());
        Assert.assertNotNull(accounts.get(9).getId());
        Assert.assertEquals(10, accounts.get(9).getId().intValue());
    }

    @Test
    public void testOffset() {

        QueryParameters q = new QueryParameters();
        q.setOffset(30);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(20, accounts.size());
        Assert.assertNotNull(accounts.get(0).getId());
        Assert.assertEquals(31, accounts.get(0).getId().intValue());
        Assert.assertNotNull(accounts.get(19).getId());
        Assert.assertEquals(50, accounts.get(19).getId().intValue());
    }

    @Test
    public void testLimitWithOffset() {

        QueryOrder qo = new QueryOrder();
        qo.setField("id");
        qo.setOrder(OrderDirection.ASC);

        QueryParameters q = new QueryParameters();
        q.setLimit(25);
        q.setOffset(0);
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(25, accounts.size());

        q.setOffset(24);

        List<AccountEntity> accountsOffseted = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accountsOffseted);
        Assert.assertEquals(25, accountsOffseted.size());

        Assert.assertNotNull(accounts.get(24).getId());
        Assert.assertNotNull(accountsOffseted.get(0).getId());
        Assert.assertEquals(accounts.get(24).getId().intValue(), accountsOffseted.get(0).getId().intValue());
//        Assert.assertEquals(65, users.get(24).getId().intValue());
    }

    @Test
    public void testLimitTooBig() {

        QueryParameters q = new QueryParameters();
        q.setLimit(300);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
    }

    @Test
    public void testOffsetOutOfBounds() {

        QueryParameters q = new QueryParameters();
        q.setOffset(200);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(0, accounts.size());
    }
}
