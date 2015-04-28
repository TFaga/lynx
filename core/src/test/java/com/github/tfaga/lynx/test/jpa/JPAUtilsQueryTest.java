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
public class JPAUtilsQueryTest {

    @ClassRule
    public static JpaRule server = new JpaRule();

    private EntityManager em = server.getEntityManager();

    @Test
    public void testEmptyQuery() {

        List<User> users = JPAUtils.queryEntities(em, User.class, new QueryParameters());
    }

    @Test
    public void testFindEntity() {

        User u = JPAUtils.getEntity(em, User.class, 1);

        Assert.assertNull(u);
    }
}
