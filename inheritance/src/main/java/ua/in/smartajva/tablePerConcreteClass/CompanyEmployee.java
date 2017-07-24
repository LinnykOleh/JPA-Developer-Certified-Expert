package ua.in.smartajva.tablePerConcreteClass;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PER_CLASS_COMPANY_EMP")
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "FULL_NAME"))
})
public class CompanyEmployee extends Employee {
    private int vacation;
}
