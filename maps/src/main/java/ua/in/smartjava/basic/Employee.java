package ua.in.smartjava.basic;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Employee (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      primary key (id))
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    /**
     * create table EMP_PHONES (
     *      Employee_id integer not null,
     *      PHONE_NUM varchar(255),
     *      PHONE_TYPE varchar(255) not null,
     *      primary key (Employee_id, PHONE_TYPE))
     *
     * alter table EMP_PHONES add constraint FKrf8h2h85lxuvtewp8ujfh3bw6 foreign key (Employee_id) references Employee (id)
     *
     */
    @ElementCollection
    @CollectionTable(name = "EMP_PHONES")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUM")
    Map<String, String> phoneNumbers;
}