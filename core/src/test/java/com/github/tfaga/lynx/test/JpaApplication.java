package com.github.tfaga.lynx.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tilen Faganel
 */
public class JpaApplication {

    private EntityManagerFactory emf;

    public void start() {

        emf = Persistence.createEntityManagerFactory("lynx");
    }

    public void stop() {

        emf.close();
    }

    public EntityManager getEntityManager() {

        return emf.createEntityManager();
    }
}
