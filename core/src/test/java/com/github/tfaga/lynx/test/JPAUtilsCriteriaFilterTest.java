package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
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
 * @since 1.1.0
 */
@RunWith(Parameterized.class)
public class JPAUtilsCriteriaFilterTest {

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
    public void testQueryWithoutCriteriaFilter() {

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, new QueryParameters());

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
    }

    @Test
    public void testQueryCriteriaFilter() {

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class,
                (p, cb, r) -> cb.and(p, cb.equal(r.get("name"), "Selena")));

        Assert.assertNotNull(accounts);
        Assert.assertEquals(1, accounts.size());

        Assert.assertNotNull(accounts.get(0));
        Assert.assertEquals("Selena", accounts.get(0).getName());

        Long accountsCount = JPAUtils.queryEntitiesCount(em, AccountEntity.class,
                (p, cb, r) -> cb.and(p, cb.equal(r.get("name"), "Selena")));

        Assert.assertNotNull(accountsCount);
        Assert.assertEquals(1, accountsCount.longValue());
    }

    @Test
    public void testQueryCriteriaFilterWithParamsAnd() {

        QueryFilter qf = new QueryFilter();
        qf.setField("address.country");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("China");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q,
                (p, cb, r) -> cb.and(p, cb.equal(r.get("name"), "Stafford")));

        Assert.assertNotNull(accounts);
        Assert.assertEquals(1, accounts.size());

        Assert.assertNotNull(accounts.get(0));
        Assert.assertEquals("Stafford", accounts.get(0).getName());

        Long accountsCount = JPAUtils.queryEntitiesCount(em, AccountEntity.class, q,
                (p, cb, r) -> cb.and(p, cb.equal(r.get("name"), "Stafford")));

        Assert.assertNotNull(accountsCount);
        Assert.assertEquals(1, accountsCount.longValue());
    }

    @Test
    public void testQueryCriteriaFilterWithParamsOr() {

        QueryFilter qf = new QueryFilter();
        qf.setField("name");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("Elena");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q,
                (p, cb, r) -> cb.or(p, cb.equal(r.get("name"), "Stafford")));

        Assert.assertNotNull(accounts);
        Assert.assertEquals(2, accounts.size());

        Assert.assertNotNull(accounts.get(0));
        Assert.assertEquals("Elena", accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(1));
        Assert.assertEquals("Stafford", accounts.get(1).getName());

        Long usersCount = JPAUtils.queryEntitiesCount(em, AccountEntity.class, q,
                (p, cb, r) -> cb.or(p, cb.equal(r.get("name"), "Stafford")));

        Assert.assertNotNull(usersCount);
        Assert.assertEquals(2, usersCount.longValue());
    }

    @Test
    public void testQueryWithCriteriaFilterAndWithFields() {

        QueryParameters q = new QueryParameters();
        q.getFields().add("name");

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q,
                (p, cb, r) -> cb.and(p, cb.equal(r.get("value"), 46)));

        Assert.assertNotNull(accounts);
        Assert.assertEquals(3, accounts.size());

        Assert.assertNotNull(accounts.get(0));
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertNull(accounts.get(0).getValue());
        Assert.assertEquals("Lauritz", accounts.get(0).getName());
    }
}
