package ua.org.smartjava.locking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import ua.org.smartjava.BaseJpaTest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LockingTest extends BaseJpaTest {

    @Before
    @Transactional
    public void init() {
        Employee john = Employee.builder()
                .id(1L)
                .name("John")
                .vacationDays(20)
                .build();
        entityManager.persist(john);
    }

    @Test
    public void testConcurrentModification() {
        entityManager.persist(bettyUpdatesVacation());
        entityManager.persist(frankUpdatesVacation());

        Employee employee = entityManager.find(Employee.class, 1L);
//        20 -12 -1
//        Action are done in one chain not concurrently
        Assert.assertEquals(7, employee.getVacationDays());
    }

    /**
     * -1 day
     */
    private Employee frankUpdatesVacation() {
        Employee employee = entityManager.find(Employee.class, 1L);
        employee.setVacationDays(employee.getVacationDays() - 1);
        return employee;
    }

    /**
     * -12 days
     */
    private Employee bettyUpdatesVacation() {
        Employee employee = entityManager.find(Employee.class, 1L);
        employee.setVacationDays(employee.getVacationDays() - 12);
        return employee;
    }

    @Test
    public void testConcurentUpdatesWithOptimisticLock() throws InterruptedException {
//        start two threads OptimisticLockException must be thrown
        new Thread(new Betty()).start();
        new Thread(new Frank()).start();
        Thread.sleep(10000);
//      TODO add verification that changes from Frank do not persist
    }

    @Test
    @Transactional
    public void testLock() {
        Employee employee = entityManager.find(Employee.class, 1L);
        entityManager.lock(employee, LockModeType.WRITE);
        employee.setVacationDays(22);
        entityManager.persist(employee);
    }

    @Test
    public void test() {
        Employee employee1 = entityManager.find(Employee.class, 1L);
        Employee employee2 = entityManager.find(Employee.class, 1L);
        entityManager.getTransaction().begin();
        employee1.setVacationDays(300);
        entityManager.persist(employee1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee2.setName("LOCK");
        entityManager.persist(employee2);
        entityManager.getTransaction().commit();
    }

    class Frank implements Runnable {

        @Transactional @Override
        public void run() {
            Employee employee = entityManager.find(Employee.class, 1L);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            employee.setVacationDays(employee.getVacationDays() - 1);

            entityManager.persist(employee);
        }
    }

    class Betty implements Runnable {

        @Override
        @Transactional
        public void run() {
            Employee employee = entityManager.find(Employee.class, 1L);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            employee.setVacationDays(employee.getVacationDays() - 12);
            entityManager.persist(employee);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}