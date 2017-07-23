package ua.in.smartajva.joinTables;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class FirstYearEmployee extends Employee {
    private int vacation;
}
