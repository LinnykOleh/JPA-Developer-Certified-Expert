package ua.in.smartajva.singleTable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@DiscriminatorColumn(name = "EMP_TYPE", discriminatorType = DiscriminatorType.STRING)
public class Employee extends CachedEntity {
    @Id
    @GeneratedValue
    private int id;

    private String name;
}
