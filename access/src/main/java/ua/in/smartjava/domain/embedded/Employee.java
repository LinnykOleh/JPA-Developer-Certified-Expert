package ua.in.smartjava.domain.embedded;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Company (
 *      id bigint not null,
 *      CMP_CITY varchar(255),
 *      CMP_COUNTRY varchar(255),
 *      name varchar(255),
 *      primary key (id))
 */

@Entity
@Table(name = "EMP")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue
    private long id;

    private String name;

//    @Embedded This is optional annotation here
    Address address;
}