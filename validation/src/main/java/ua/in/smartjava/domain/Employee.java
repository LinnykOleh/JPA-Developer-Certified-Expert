package ua.in.smartjava.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.in.smartjava.constraint.Street;
import ua.in.smartjava.validationGroups.Personal;
import ua.in.smartjava.validationGroups.StreetGroup;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Street can not be null")
    @Street(value = "BackerStreet", groups = StreetGroup.class)
    private String street;

    @Size(min = 1 ,max = 2, groups = Personal.class)
    private String district;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cars> cars;

}