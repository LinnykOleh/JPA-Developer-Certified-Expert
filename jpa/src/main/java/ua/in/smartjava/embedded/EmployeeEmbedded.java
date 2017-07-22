package ua.in.smartjava.embedded;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeEmbedded {
    @Id
    private long id;

    private String name;

    @Embedded
    Address address;
}