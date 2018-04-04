package com.github.tfaga.lynx.test.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
@Embeddable
public class DocumentIdEntity implements Serializable {

    @Column(name = "key")
    private Integer key;

    @Column(name = "version")
    private Integer version;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
