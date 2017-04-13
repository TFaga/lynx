package com.github.tfaga.lynx.test.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tilen Faganel
 * @since 1.0.1
 */
public class JpaUtil {

    private static JpaUtil instance;

    private EntityManagerFactory emfEclipseLink;
    private EntityManagerFactory emfHibernate;

    public static JpaUtil getInstance() {
        if (instance == null) {
            instance = new JpaUtil();
        }

        return instance;
    }

    private JpaUtil() {

        emfEclipseLink = Persistence.createEntityManagerFactory("lynx-eclipselink");
        emfHibernate = Persistence.createEntityManagerFactory("lynx-hibernate");
    }

    public EntityManager getEclipselinkEntityManager() {
        return emfEclipseLink.createEntityManager();
    }

    public EntityManager getHibernateEntityManager() {
        return emfHibernate.createEntityManager();
    }
}
