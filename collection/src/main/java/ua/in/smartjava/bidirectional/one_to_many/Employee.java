package ua.in.smartjava.bidirectional.one_to_many;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table EMP_1_TO_MANY_BI (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      DEPT_ID integer,
 *      employees_order integer
 *      primary key (id))
 * alter table EMP_1_TO_MANY_BI add constraint FK7brnrkavpyrh18c9mrebnf5ug foreign key (DEPT_ID) references DEP_1_TO_MANY_BI (id)
 */
@Entity
@Table(name = "EMP_1_TO_MANY_BI")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEPT_ID")
    private Department department;
}
