package com.github.tfaga.lynx.interfaces;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @since 1.1.0
 */
@FunctionalInterface
public interface CriteriaFilter<X> {

    Predicate createPredicate(Predicate p, CriteriaBuilder cb, Root<X> r);
}
