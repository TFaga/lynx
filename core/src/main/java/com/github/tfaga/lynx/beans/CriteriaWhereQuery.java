package com.github.tfaga.lynx.beans;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;

/**
 * @author Tilen Faganel
 * @since 1.0.0
 */
public class CriteriaWhereQuery implements Serializable {

    private final static long serialVersionUID = 1L;

    private Predicate predicate;
    private Boolean containsToMany;

    public CriteriaWhereQuery(Predicate predicate, Boolean containsToMany) {
        this.predicate = predicate;
        this.containsToMany = containsToMany;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public Boolean containsToMany() {
        return containsToMany;
    }
}
