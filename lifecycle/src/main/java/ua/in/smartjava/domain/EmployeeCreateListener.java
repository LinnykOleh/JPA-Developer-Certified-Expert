package ua.in.smartjava.domain;

import javax.persistence.PostPersist;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeCreateListener {

    @PostPersist
    public void postPersist(Employee employee) {
        log.error("Listener on {} is invoked" + employee.getStreet() );
    }
}
