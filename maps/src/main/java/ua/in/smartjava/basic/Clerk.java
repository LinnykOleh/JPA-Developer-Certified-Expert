package ua.in.smartjava.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Clerk (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      department_id integer,
 *      clerksByDepartment_KEY varchar(255),
 *      primary key (id))
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clerk {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private Department department;
}
