package ua.org.smartjava.primaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class EmpTable {

    @TableGenerator(name = "EmpTableGenerator",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "Add_Gen",
            initialValue = 1000,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(generator = "EmpTableGenerator", strategy = GenerationType.TABLE)
    private long id;

    public long getId() {
        return id;
    }
}
