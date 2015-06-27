package com.github.tfaga.lynx.utils;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.OrderDirection;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
public class JPAUtils {

    private static final Logger log = Logger.getLogger(JPAUtils.class.getSimpleName());

    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity) {

        return queryEntities(em, entity, new QueryParameters());
    }

    public static <T> Long queryEntitiesCount(EntityManager em, Class<T> entity) {

        return queryEntitiesCount(em, entity, new QueryParameters());
    }

    public static <T> List<T> queryEntities(EntityManager em, Class<T> entity, QueryParameters q) {

        if (q == null)
            throw new IllegalArgumentException("Query parameters can't be null. " +
                    "If you don't have any parameters either pass a empty object or " +
                    "use the queryEntities(EntityManager, Class<T>) method.");

        log.finest("Querying entity: '" + entity.getSimpleName() + "' with parameters: " + q);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(entity);

        CriteriaQuery<Tuple> ct = cb.createTupleQuery();

        Root<T> r = cq.from(entity);

        if (!q.getFilters().isEmpty()) {

            Predicate whereQuery = createWhereQuery(cb, r, q);

            cq.where(whereQuery);
            ct.where(whereQuery);
        }

        if (!q.getOrder().isEmpty()) {

            List<Order> orders = createOrderQuery(cb, r, q);

            cq.orderBy(orders);
            ct.orderBy(orders);
        }

        cq.select(r);
        ct.multiselect(createFieldsSelect(r, q, getEntityIdField(em, entity)));

        TypedQuery<T> tq = em.createQuery(cq);
        TypedQuery<Tuple> tqt = em.createQuery(ct);

        if (q.getLimit() != null && q.getLimit() > -1) {

            tq.setMaxResults(q.getLimit().intValue());
            tqt.setMaxResults(q.getLimit().intValue());
        }

        if (q.getOffset() != null && q.getOffset() > -1) {

            tq.setFirstResult(q.getOffset().intValue());
            tqt.setFirstResult(q.getOffset().intValue());
        }

        if (q.getFields().isEmpty()) {

            return tq.getResultList();
        } else {

            return createEntityFromTuple(tqt.getResultList(), entity);
        }
    }

    public static <T> Long queryEntitiesCount(EntityManager em, Class<T> entity, QueryParameters
            q) {

        if (q == null)
            throw new IllegalArgumentException("Query parameters can't be null. " +
                    "If you don't have any parameters either pass a empty object or " +
                    "use the queryEntitiesCount(EntityManager, Class<T>) method.");

        log.finest("Querying entity count: '" + entity.getSimpleName() + "' with parameters: " + q);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<T> r = cq.from(entity);

        if (!q.getFilters().isEmpty()) {

            cq.where(createWhereQuery(cb, r, q));
        }

        cq.select(cb.count(r));

        return em.createQuery(cq).getSingleResult();
    }

    public static List<Order> createOrderQuery(CriteriaBuilder cb, Root<?> r, QueryParameters q) {

        List<Order> orders = new ArrayList<>();

        q.getOrder().stream().filter(qo -> qo.getField() != null).forEach(qo -> {

            try {

                Path<String> field = getCriteraField(qo.getField(), r);

                if (qo.getOrder() == OrderDirection.DESC) {

                    orders.add(cb.desc(field));
                } else {

                    orders.add(cb.asc(field));
                }
            } catch (IllegalArgumentException e) {

                throw new NoSuchEntityFieldException(e.getMessage(), qo.getField());
            }
        });

        return orders;
    }

    public static Predicate createWhereQuery(CriteriaBuilder cb, Root<?> r, QueryParameters q) {

        Predicate predicate = cb.conjunction();

        for (QueryFilter f : q.getFilters()) {

            if (f.getValue() == null && f.getDateValue() == null && f.getValues().isEmpty())
                continue;

            Predicate np = null;

            try {

                Path<String> stringField = getCriteraField(f.getField(), r);
                Path<Date> dateField = getCriteraField(f.getField(), r);

                switch (f.getOperation()) {

                    case EQ:
                        np = cb.equal(stringField,
                                f.getDateValue() == null ? f.getValue() : f
                                        .getDateValue());
                        break;
                    case EQIC:
                        np = cb.equal(cb.lower(stringField), f.getDateValue() == null ? f
                                .getValue().toLowerCase() : f.getDateValue());
                        break;
                    case NEQ:
                        np = cb.notEqual(stringField,
                                f.getDateValue() == null ? f.getValue() : f
                                        .getDateValue());
                        break;
                    case NEQIC:
                        np = cb.notEqual(cb.lower(stringField), f.getDateValue() == null ? f
                                .getValue().toLowerCase() : f.getDateValue());
                        break;
                    case LIKE:
                        np = cb.like(stringField, f.getValue());
                        break;
                    case LIKEIC:
                        np = cb.like(cb.lower(stringField), f.getValue().toLowerCase());
                        break;
                    case GT:
                        if (f.getDateValue() != null) {
                            np = cb.greaterThan(dateField, f.getDateValue());
                        } else {
                            np = cb.greaterThan(stringField, f.getValue());
                        }
                        break;
                    case GTE:
                        if (f.getDateValue() != null) {
                            np = cb.greaterThanOrEqualTo(dateField, f.getDateValue());
                        } else {
                            np = cb.greaterThanOrEqualTo(stringField, f.getValue());
                        }
                        break;
                    case LT:
                        if (f.getDateValue() != null) {
                            np = cb.lessThan(dateField, f.getDateValue());
                        } else {
                            np = cb.lessThan(stringField, f.getValue());
                        }
                        break;
                    case LTE:
                        if (f.getDateValue() != null) {
                            np = cb.lessThanOrEqualTo(dateField, f.getDateValue());
                        } else {
                            np = cb.lessThanOrEqualTo(stringField, f.getValue());
                        }
                        break;
                    case IN:
                        np = stringField.in(f.getValues());
                        break;
                    case INIC:
                        np = cb.lower(stringField)
                                .in(f.getValues().stream().map(String::toLowerCase)
                                        .collect(Collectors.toList()));
                        break;
                    case NIN:
                        np = cb.not(stringField.in(f.getValues()));
                        break;
                    case NINIC:
                        np = cb.not(cb.lower(stringField)
                                .in(f.getValues().stream().map(String::toLowerCase)
                                        .collect(Collectors.toList())));
                        break;
                }
            } catch (IllegalArgumentException e) {

                throw new NoSuchEntityFieldException(e.getMessage(), f.getField());
            }

            predicate = cb.and(predicate, np);
        }

        return predicate;
    }

    public static List<Selection<?>> createFieldsSelect(Root<?> r, QueryParameters q, String
            idField) {

        List<Selection<?>> fields = q.getFields().stream().map(f -> {

            try {
                return r.get(f).alias(f);
            } catch (IllegalArgumentException e) {

                throw new NoSuchEntityFieldException(e.getMessage(), f);
            }
        }).collect(Collectors.toList());

        try {
            fields.add(r.get(idField).alias(idField));
        } catch (IllegalArgumentException e) {

            throw new NoSuchEntityFieldException(e.getMessage(), idField);
        }

        return fields.stream().distinct().collect(Collectors.toList());
    }

    private static <T> List<T> createEntityFromTuple(List<Tuple> tuples, Class<T> entity) {

        List<T> entities = new ArrayList<>();

        for (Tuple t : tuples) {

            T el;

            try {
                el = entity.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException |
                    NoSuchMethodException | InvocationTargetException e) {

                throw new AssertionError();
            }

            for (TupleElement<?> te : t.getElements()) {

                Object o = t.get(te);

                try {
                    Field f = getFieldFromEntity(entity, te.getAlias());
                    f.setAccessible(true);
                    f.set(el, o);
                } catch (NoSuchFieldException | IllegalAccessException e1) {

                    throw new NoSuchEntityFieldException(e1.getMessage(), te.getAlias());
                }
            }

            entities.add(el);
        }

        return entities;
    }

    @SuppressWarnings("unchecked")
    private static String getEntityIdField(EntityManager em, Class entity) {

        String idProperty = "";

        Metamodel metamodel = em.getMetamodel();
        EntityType e = metamodel.entity(entity);
        Set<SingularAttribute> singularAttributes = e.getSingularAttributes();

        for (SingularAttribute singularAttribute : singularAttributes) {

            if (singularAttribute.isId()) {

                idProperty = singularAttribute.getName();
                break;
            }
        }

        return idProperty;
    }

    private static Field getFieldFromEntity(Class entity, String fieldName) throws
            NoSuchFieldException {

        try {
            return entity.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {

            if (entity.getSuperclass() == null) {
                throw e;
            }

            return getFieldFromEntity(entity.getSuperclass(), fieldName);
        }
    }

    private static <G> Path<G> getCriteraField(String fieldName, Root<?> r) {

        if (fieldName == null) fieldName = "";

        String fields[] = fieldName.split("\\.");

        Path<G> path = r.get(fields[0]);

        for (int i = 1; i < fields.length; i++) {

            path = path.get(fields[i]);
        }

        return path;
    }
}
