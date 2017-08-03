package ua.in.smartjava.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Inheritance
public class CarBroken extends UnMappedSuperClass {
    @Id
    @GeneratedValue
    @Getter
    private long id;

    @PrePersist
    public void prePersist() {
        log.error("PrePersist CarBroken {}", this.getClass().getSimpleName());

    }
}
