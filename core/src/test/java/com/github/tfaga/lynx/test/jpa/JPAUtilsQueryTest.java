package com.github.tfaga.lynx.test.jpa;

import com.github.tfaga.lynx.test.JpaApplication;
import com.github.tfaga.lynx.test.jpa.entities.User;
import com.github.tfaga.lynx.utils.JPAUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * @author Tilen Faganel
 */
public class JPAUtilsQueryTest {

    private static JpaApplication app;

    private EntityManager em;

    @BeforeClass
    public static void setUpApp() {

        app = new JpaApplication();
        app.start();
    }

    @AfterClass
    public static void tearDownApp() {

        app.stop();
    }

    @Before
    public void setUpEm() {

        em = app.getEntityManager();
    }

    @After
    public void tearDownEm() {

        em.close();
    }

    @Test
    public void testCreateEntity() {

        User u = new User();

        u = JPAUtils.createEntity(em, u);

        Assert.assertNotNull(u);
        Assert.assertNotNull(u.getId());
        Assert.assertEquals(u.getId().intValue(), 1);
    }
}
