package ua.in.smartjava.unidirectional.many_to_one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Department (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      primary key (id))
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private int id;

    private String name;
}
