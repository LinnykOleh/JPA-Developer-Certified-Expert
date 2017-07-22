package ua.in.smartjava.domain.primaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

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
