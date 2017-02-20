package ua.org.smartjava.domain.primaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class EmpSequence {
    @SequenceGenerator(
            name = "Emp_Seq_Gen",
            sequenceName = "Emp_Seq")
    @Id
    @GeneratedValue(generator = "Emp_Seq_Gen")
    private long id;
    
}