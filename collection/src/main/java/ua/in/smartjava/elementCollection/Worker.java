package ua.in.smartjava.elementCollection;

import java.util.Collection;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 * create table Worker (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      primary key (id))
 *
 * create table Worker_nickNames (
 *      Worker_id integer not null,
 *      nickNames varchar(255))
 *
 * alter table Worker_nickNames add constraint FKf2bue2utf8kd4a1vsuxw2j8kr foreign key (Worker_id) references Worker (id)
 *
 * create table WORKER_VACATIONS (
 *      WORKER_ID integer not null,
 *      VACATION_DAYS integer,
 *      VACATION_PLACE varchar(255))
 *
 * alter table WORKER_VACATIONS add constraint FK6o64lm2aqabjauqqu8qq9i0dn foreign key (WORKER_ID) references Worker (id)
 */
@Entity
public class Worker {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "WORKER_VACATIONS",
            joinColumns = @JoinColumn(name = "WORKER_ID"))
    @AttributeOverrides({
            @AttributeOverride(name = "days", column = @Column(name = "VACATION_DAYS")),
            @AttributeOverride(name = "place", column = @Column(name = "VACATION_PLACE"))
    })
    private Collection<Vacation> vacations;

    @ElementCollection
    private Set<String> nickNames;


}
