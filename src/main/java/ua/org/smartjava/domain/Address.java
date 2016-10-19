package ua.org.smartjava.domain;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    private long id;

    private String street;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Address() {
    }
}