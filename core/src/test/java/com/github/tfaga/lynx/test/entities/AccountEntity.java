package com.github.tfaga.lynx.test.entities;

import javax.persistence.*;
import java.util.List;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;

    @Column(name = "size")
    private Integer size;

    @Embedded
    private AddressEntity address;

    @OneToMany(mappedBy = "account")
    private List<DocumentEntity> documents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentEntity> documents) {
        this.documents = documents;
    }
}
