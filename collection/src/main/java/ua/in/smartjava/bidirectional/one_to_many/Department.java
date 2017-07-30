package ua.in.smartjava.bidirectional.one_to_many;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

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
@Table(name = "DEP_1_TO_MANY_BI")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    // If you forgot to add mappedBy - then additional JoinTable will be created.
    // We will have 2 unidirectional mapping.
    @OneToMany(mappedBy = "department")
    @OrderColumn
    private List<Employee> employees;
}
