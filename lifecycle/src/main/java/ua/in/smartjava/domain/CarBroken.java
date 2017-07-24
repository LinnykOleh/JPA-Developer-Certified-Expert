package ua.in.smartjava.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
public class CarBroken extends UnMappedSuperClass {
    @Id
    @GeneratedValue
    @Getter
    private long id;
}
