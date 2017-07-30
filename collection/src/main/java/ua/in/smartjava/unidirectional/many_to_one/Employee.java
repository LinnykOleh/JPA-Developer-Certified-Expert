package ua.in.smartjava.unidirectional.many_to_one;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Employee (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      DEPT_ID integer,
 *      primary key (id))
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    // Owning side
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEPT_ID")
    private Department department;
}
