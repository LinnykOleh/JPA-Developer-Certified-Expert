package ua.in.smartajva.singleTable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "FULL_TIME")
public class FullTimeEmloyee extends CompanyEmployee {
    private int salary;
}
