package com.github.tfaga.lynx.utils;

import com.github.tfaga.lynx.beans.QueryParameters;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Tilen
 */
public class JPAUtils {


    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity, QueryParameters q) {

        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entity);

        Root<T> r = cq.from(entity);

        cq.select(r);

        TypedQuery<T> tq = em.createQuery(cq);

        if (q.getLimit() != null) {

            tq.setMaxResults(q.getLimit().intValue());
        }

        if (q.getOffset() != null) {

            tq.setFirstResult(q.getOffset().intValue());
        }

        List<T> results = tq.getResultList();

        return results;
    }

    public static <T> T getEntity(EntityManager em, Class<T> entity, Object id) {

        return em.find(entity, id);
    }

    public static <T> T createEntity(EntityManager em, T object) {

        em.persist(object);

        return object;
    }
}
