package ua.org.smartjava.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String country;
}