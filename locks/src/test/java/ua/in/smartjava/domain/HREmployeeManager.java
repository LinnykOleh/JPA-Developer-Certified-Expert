package ua.in.smartjava.domain;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HREmployeeManager implements Runnable {

    private final EntityManager entityManager;
    private final int employeeId;
    private final int minusDays;
    private final int sleepMsec;

    public HREmployeeManager(EntityManager entityManager, int employeeId, int minusDays, int sleepMsec) {
        this.entityManager = entityManager;
        this.employeeId = employeeId;
        this.minusDays = minusDays;
        this.sleepMsec = sleepMsec;
    }

    @Override
    public void run() {
        try {
            entityManager.getTransaction().begin();

            Employee employee = entityManager.find(Employee.class, employeeId);
            try {
                Thread.sleep(sleepMsec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("HR: {}, employee vacationDays before: {}", this.getClass().getSimpleName(), employee
                    .getVacationDays());
            employee.setVacationDays(employee.getVacationDays() - minusDays);
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            log.error("Error on HR: {}, {}", this.getClass().getSimpleName(), ex.getMessage());
        }
    }
}