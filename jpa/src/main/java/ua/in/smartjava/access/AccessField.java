package ua.in.smartjava.access;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class AccessField {
    @Id
    long id;
    String name;
}