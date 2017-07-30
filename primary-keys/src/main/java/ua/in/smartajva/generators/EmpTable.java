package ua.in.smartajva.generators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * create table ID_GEN (
 *      GEN_NAME varchar(255) not null,
 *      GEN_VAL bigint,
 *      primary key (GEN_NAME))
 *
 * create table EmpTable (
 *      id bigint not null,
 *      primary key (id))
 */
@Entity
public class EmpTable {

    @TableGenerator(
            name = "EmpTableGenerator",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "EmpTable_Gen",
            initialValue = 100,
            allocationSize = 10
    )
    @Id
    @GeneratedValue(generator = "EmpTableGenerator", strategy = GenerationType.TABLE)
    private long id;

    public long getId() {
        return id;
    }
}
