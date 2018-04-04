package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.exceptions.InvalidEntityFieldException;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.AccountEntity;
import com.github.tfaga.lynx.test.entities.DocumentEntity;
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
        qo.setField("name");
        qo.setOrder(OrderDirection.ASC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertEquals("Aurilia", accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(49).getName());
        Assert.assertEquals("Zea", accounts.get(49).getName());
    }

    @Test
    public void testSingleOrderDesc() {

        QueryOrder qo = new QueryOrder();
        qo.setField("value");
        qo.setOrder(OrderDirection.DESC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertEquals(98, accounts.get(0).getValue().intValue());
        Assert.assertNotNull(accounts.get(49).getName());
        Assert.assertEquals(8, accounts.get(49).getValue().intValue());
    }

    @Test
    public void testMultipleOrders() {

        QueryParameters q = new QueryParameters();

        QueryOrder qo = new QueryOrder();
        qo.setField("value");
        qo.setOrder(OrderDirection.DESC);
        q.getOrder().add(qo);

        qo = new QueryOrder();
        qo.setField("name");
        qo.setOrder(OrderDirection.ASC);
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getValue());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertEquals(98, accounts.get(0).getValue().intValue());
        Assert.assertEquals("Basile", accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(1).getValue());
        Assert.assertNotNull(accounts.get(1).getName());
        Assert.assertEquals(98, accounts.get(1).getValue().intValue());
        Assert.assertEquals("Kathleen", accounts.get(1).getName());
    }

    @Test
    public void testNullDirection() {

        QueryOrder qo = new QueryOrder();
        qo.setField("name");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertEquals("Aurilia", accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(49).getName());
        Assert.assertEquals("Zea", accounts.get(49).getName());
    }

    @Test
    public void testNullField() {

        QueryOrder qo = new QueryOrder();

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q)
                .stream().sorted(Comparator.comparing(AccountEntity::getId)).collect(Collectors.toList());

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getName());
        Assert.assertEquals("Evvie", accounts.get(0).getName());
        Assert.assertNotNull(accounts.get(49).getName());
        Assert.assertEquals("Cari", accounts.get(49).getName());
    }

    @Test
    public void testNonExistentColumn() {

        QueryOrder qo = new QueryOrder();
        qo.setField("lstnm");
        qo.setOrder(OrderDirection.DESC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        try {

            JPAUtils.queryEntities(em, AccountEntity.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("lstnm", e.getField());
        }
    }

    @Test
    public void testCaseSensitiveField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("NAmE");
        qo.setOrder(OrderDirection.ASC);

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        try {

            JPAUtils.queryEntities(em, AccountEntity.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("NAmE", e.getField());
        }
    }

    @Test
    public void testManyToOne() {

        QueryOrder qo = new QueryOrder();
        qo.setField("account.name");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
        Assert.assertNotNull(documents.get(0).getAccount().getName());
        Assert.assertEquals("Aurilia", documents.get(0).getAccount().getName());
        Assert.assertNotNull(documents.get(49).getAccount().getName());
        Assert.assertEquals("Zea", documents.get(49).getAccount().getName());
    }

    @Test
    public void testManyToOneOnlyField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("account");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
        Assert.assertNotNull(documents.get(0).getAccount().getName());
        Assert.assertEquals(1, documents.get(0).getAccount().getId().intValue());
        Assert.assertNotNull(documents.get(49).getAccount().getName());
        Assert.assertEquals(49, documents.get(49).getAccount().getId().intValue());
    }

    @Test
    public void testEmbedded() {

        QueryOrder qo = new QueryOrder();
        qo.setField("address.country");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(50, accounts.size());
        Assert.assertNotNull(accounts.get(0).getAddress());
        Assert.assertNotNull(accounts.get(0).getAddress().getCountry());
        Assert.assertEquals("Argentina", accounts.get(0).getAddress().getCountry());
        Assert.assertNotNull(accounts.get(49).getAddress());
        Assert.assertNotNull(accounts.get(49).getAddress().getCountry());
        Assert.assertEquals("Venezuela", accounts.get(49).getAddress().getCountry());
    }

    @Test
    public void testEmbeddedId() {

        QueryOrder qo = new QueryOrder();
        qo.setField("id.version");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
        Assert.assertNotNull(documents.get(0).getId());
        Assert.assertNotNull(documents.get(0).getId().getVersion());
        Assert.assertEquals(1, documents.get(0).getId().getVersion().intValue());
        Assert.assertNotNull(documents.get(49).getId());
        Assert.assertNotNull(documents.get(49).getId().getVersion());
        Assert.assertEquals(5, documents.get(49).getId().getVersion().intValue());
    }

    @Test(expected = InvalidEntityFieldException.class)
    public void testOneToMany() {

        QueryOrder qo = new QueryOrder();
        qo.setField("documents.string");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        JPAUtils.queryEntities(em, AccountEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test(expected = InvalidEntityFieldException.class)
    public void testOneToManyOnlyField() {

        QueryOrder qo = new QueryOrder();
        qo.setField("documents");

        QueryParameters q = new QueryParameters();
        q.getOrder().add(qo);

        JPAUtils.queryEntities(em, AccountEntity.class, q);
        Assert.fail("No exception was thrown");
    }
}
