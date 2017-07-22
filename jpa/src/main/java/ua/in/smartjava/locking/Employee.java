package ua.in.smartjava.locking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Version;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "LockEmployee")
@Getter @Setter
@Builder
@ToString
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    @Id
    private long id;

    private String name;

    private int vacationDays;

    @PostPersist
    public void showEmployeeDetails() {
        LOGGER.error(this.toString());
    }

    @Version
    private int version;
}