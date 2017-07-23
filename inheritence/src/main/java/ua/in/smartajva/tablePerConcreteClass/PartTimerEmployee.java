package ua.in.smartajva.tablePerConcreteClass;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PER_CLASS_PART")
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "PART_NAME"))
})
public class PartTimerEmployee extends Employee {
    private int time;
}
