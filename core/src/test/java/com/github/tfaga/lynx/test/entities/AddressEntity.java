package com.github.tfaga.lynx.test.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
@Embeddable
public class AddressEntity {

    @Column(name = "country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
