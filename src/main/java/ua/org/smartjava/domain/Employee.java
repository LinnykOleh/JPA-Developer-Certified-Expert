package ua.org.smartjava.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Employee {
    @Transient
    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);
    @Id
    long Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressID")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany
    @JoinColumn(name = "phoneID")
    Collection<Phone> phones;

    public Collection<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Collection<Phone> phones) {
        this.phones = phones;
    }

    String name;

    public Employee(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Employee() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("PostConstruct");
        System.err.println("PreConstruct");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy");
    }
}