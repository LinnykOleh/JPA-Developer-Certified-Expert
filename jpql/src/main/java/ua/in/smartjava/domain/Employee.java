package ua.in.smartjava.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedQueries({
        @NamedQuery(name = "Employee.findEmployeeNameById",
                query = "SELECT e.name FROM Employee e WHERE e.id = :empId"
        ),
        @NamedQuery(name = "Employee.findEmployeeById",
                query = "SELECT e FROM Employee e WHERE e.id = :empId"),
        @NamedQuery(name = "Emloyee.findByDate",
        query = "SELECT e FROM Employee e WHERE e.employmentDate = :empDate")
}
)
public class Employee {
    @Transient
    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date employmentDate;

}