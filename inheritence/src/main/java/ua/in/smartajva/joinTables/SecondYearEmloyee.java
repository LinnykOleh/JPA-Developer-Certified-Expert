package ua.in.smartajva.joinTables;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("2")
public class SecondYearEmloyee extends FirstYearEmployee {
    private int salary;
}
