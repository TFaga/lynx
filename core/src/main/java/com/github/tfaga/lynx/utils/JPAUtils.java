package com.github.tfaga.lynx.utils;

import com.github.tfaga.lynx.beans.QueryOrder;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @author Tilen
 */
public class JPAUtils {

    private static final Logger log = Logger.getLogger(JPAUtils.class.getSimpleName());

    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity) {

        return queryEntities(em, entity, new QueryParameters());
    }

    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity, QueryParameters q) {

        if (q == null)
            throw new IllegalArgumentException("Query parameters can't be null. " +
                    "If you don't have any parameters either pass a empty object or " +
                    "use the queryEntities(EntityManager, Class<T>) method.");

        log.finest("Querying entity: '" + entity.getSimpleName() + "' with parameters: " + q);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(entity);

        Root<T> r = cq.from(entity);

        if (!q.getOrder().isEmpty()) {

            cq.orderBy(createOrderQuery(cb, r, q));
        }

        cq.select(r);

        TypedQuery<T> tq = em.createQuery(cq);

        if (q.getLimit() != null && q.getLimit() > -1) {

            tq.setMaxResults(q.getLimit().intValue());
        }

        if (q.getOffset() != null && q.getOffset() > -1) {

            tq.setFirstResult(q.getOffset().intValue());
        }

        return tq.getResultList();
    }

    public static <T> T getEntity(EntityManager em, Class<T> entity, Object id) {

        return em.find(entity, id);
    }

    public static <T> T createEntity(EntityManager em, T object) {

        em.persist(object);

        return object;
    }

    private static List<Order> createOrderQuery(CriteriaBuilder cb, Root<?> r, QueryParameters q) {

        List<Order> orders = new ArrayList<>();

        q.getOrder().stream().filter(qo -> qo.getField() != null).forEach(qo -> {

            try {

                if (qo.getOrder() == OrderDirection.DESC) {

                    orders.add(cb.desc(r.get(qo.getField())));
                } else {

                    orders.add(cb.asc(r.get(qo.getField())));
                }
            } catch (IllegalArgumentException e) {

                throw new NoSuchEntityFieldException(e.getMessage(), qo.getField());
            }
        });

        return orders;
    }
}
