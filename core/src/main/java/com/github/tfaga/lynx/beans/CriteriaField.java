package com.github.tfaga.lynx.beans;

import javax.persistence.criteria.Path;
import java.io.Serializable;

/**
 * @author Tilen Faganel
 * @since 1.0.0
 */
public class CriteriaField implements Serializable {

    private final static long serialVersionUID = 1L;

    private Path path;
    private Boolean containsToMany;

    public CriteriaField(Path path, Boolean containsToMany) {
        this.path = path;
        this.containsToMany = containsToMany;
    }

    public Path getPath() {
        return path;
    }

    public Boolean containsToMany() {
        return containsToMany;
    }
}
