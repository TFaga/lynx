package com.github.tfaga.lynx.test.rules;

import org.junit.rules.ExternalResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaRule extends ExternalResource {

    private EntityManager em;

    @Override
    protected void before() throws Throwable {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lynx");

        em = emf.createEntityManager();
    }

    @Override
    protected void after() {

        em.close();
    }

    public EntityManager getEntityManager() {

        return em;
    }
}
