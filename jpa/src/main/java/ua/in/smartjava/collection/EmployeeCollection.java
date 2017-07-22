package ua.in.smartjava.collection;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.Setter;

@Entity
public class EmployeeCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter @Setter
    private String name;

    @ElementCollection(targetClass = VacationEntry.class)
    @CollectionTable(
            name = "VACATION",
            joinColumns = @JoinColumn(name = "EMP_ID")
    )
    @Getter @Setter
    @OrderBy("startDate ASC")
    private Collection vacationEntries;

    @ElementCollection
    @Getter @Setter
    @Column(name = "NICKNAME")
    private Set<String> nickNames;

}