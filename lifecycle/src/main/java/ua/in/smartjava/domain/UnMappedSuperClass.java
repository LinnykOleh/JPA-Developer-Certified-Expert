package ua.in.smartjava.domain;

import javax.persistence.PostPersist;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnMappedSuperClass {

    @PostPersist
    public void init() {
        log.info("PostPersist {}", this.getClass().getSimpleName());
    }
}
