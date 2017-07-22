package ua.in.smartjava.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String country;
}