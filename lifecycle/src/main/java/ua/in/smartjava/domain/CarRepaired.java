package ua.in.smartjava.domain;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
public class CarRepaired extends CarBroken {

    @PrePersist
    public void prePersist() {
        log.error("PrePersist CarRepaired {}", this.getClass().getSimpleName());

    }

}
