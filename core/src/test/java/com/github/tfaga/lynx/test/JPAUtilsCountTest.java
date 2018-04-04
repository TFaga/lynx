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

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class JPAUtilsCountTest {

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
    public void testEmptyQueryCount() {

        Long count = JPAUtils.queryEntitiesCount(em, AccountEntity.class);

        Assert.assertNotNull(count);
        Assert.assertEquals((long)50, count.longValue());
    }

    @Test
    public void testQueryCount() {

        QueryParameters q = new QueryParameters();

        QueryFilter qf = new QueryFilter();
        qf.setField("name");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Caryl");
        qf.getValues().add("Jarred");
        qf.getValues().add("Hamlin");
        qf.getValues().add("Retha");
        q.getFilters().add(qf);

        qf = new QueryFilter();
        qf.setField("address.country");
        qf.setOperation(FilterOperation.LIKE);
        qf.setValue("C%");
        q.getFilters().add(qf);

        Long count = JPAUtils.queryEntitiesCount(em, AccountEntity.class, q);

        Assert.assertNotNull(count);
        Assert.assertEquals((long)2, count.longValue());
    }
}
