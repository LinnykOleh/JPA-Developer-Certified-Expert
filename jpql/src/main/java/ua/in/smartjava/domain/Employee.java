package ua.in.smartjava.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    @Transient
    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue
    private long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "addressID")
//    private Address address;

//    public Address getAddress() {
//        return address;
//    }

//    public void setAddress(Address address) {
//        this.address = address;
//    }

//    @OneToMany
//    @JoinColumn(name = "phoneID")
//    Collection<Phone> phones;

//    public Collection<Phone> getPhones() {
//        return phones;
//    }

//    public void setPhones(Collection<Phone> phones) {
//        this.phones = phones;
//    }

    private String name;

}