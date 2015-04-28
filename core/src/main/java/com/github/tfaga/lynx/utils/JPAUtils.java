package com.github.tfaga.lynx.utils;

import com.github.tfaga.lynx.beans.QueryParameters;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * @author Tilen
 */
public class JPAUtils {


    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity, QueryParameters q) {

        return null;
    }

    public static <T> T getEntity(EntityManager em, Class<T> entity, Object id) {

        return em.find(entity, id);
    }
}
