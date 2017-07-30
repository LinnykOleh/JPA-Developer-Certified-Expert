package ua.in.smartjava.domain.access;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * create table FieldAccess (
 *      id bigint not null,
 *      name varchar(255),
 *      primary key (id))
 */
@Entity
@Access(AccessType.FIELD)
public class FieldAccess {
    @Id
    long id;
    String name;

    public FieldAccess(String name) {
        this.name = name;
    }
}