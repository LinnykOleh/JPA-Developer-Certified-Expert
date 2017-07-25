package ua.in.smartjava.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LocksTest {

    private static final String STREET = "Baker Street";
    private static final String CITY = "London";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @After
    @Transactional
    public void tearDown() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE from Employee e");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * This is like undo discard changes.
     */
    @Test
    public void testRefresh() {
        //Prepare data
        entityManager.getTransaction().begin();
        Employee emp = Employee.builder().street(STREET).district(CITY).build();
        entityManager.persist(emp);
        entityManager.getTransaction().commit();

        Employee loadedEmployee = entityManager.find(Employee.class, emp.getId());
        loadedEmployee.setStreet("NONE");
        loadedEmployee.setDistrict("NONE");
        entityManager.refresh(loadedEmployee);
        //ua.in.smartjava.domain.Employee          : PostLoad

        Assert.assertEquals(STREET, loadedEmployee.getStreet());
        Assert.assertEquals(CITY, loadedEmployee.getDistrict());
    }

    /**
     * This test shows the problems with concurrent access
     */
    @Test
    public void testNoConcurrentAccess() throws InterruptedException {
        //Prepare data
        entityManager.getTransaction().begin();
        Employee employee = Employee.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        HREmployeeManager alice = new HREmployeeManager(this.entityManagerFactory.createEntityManager(), employee.getId(), 10, 1000);
        HREmployeeManager bob = new HREmployeeManager(this.entityManagerFactory.createEntityManager(), employee.getId(), 1, 3000);
        new Thread(alice).start();
        new Thread(bob).start();
        Thread.sleep(4000);

        entityManager.refresh(employee);
        Assert.assertEquals(19, employee.getVacationDays());
    }

    /**
     * Optimistic Lock work example

     First Manager:
     update employee_optimistic set district=?, street=?, vacation_days=?, version=? where id=? and version=?
     binding parameter [1] as [VARCHAR] - [London]
     binding parameter [2] as [VARCHAR] - [Baker Street]
     binding parameter [3] as [INTEGER] - [10]
     binding parameter [4] as [INTEGER] - [1]
     binding parameter [5] as [INTEGER] - [1]
     binding parameter [6] as [INTEGER] - [0]
     update ... set version=1 where ... version=0

     Second Manager:
     update employee_optimistic set district=?, street=?, vacation_days=?, version=? where id=? and version=?
     binding parameter [1] as [VARCHAR] - [London]
     binding parameter [2] as [VARCHAR] - [Baker Street]
     binding parameter [3] as [INTEGER] - [19]
     binding parameter [4] as [INTEGER] - [1]
     binding parameter [5] as [INTEGER] - [1]
     binding parameter [6] as [INTEGER] - [0]
     update ... set version=1 where ... version=0 => no
     JDBC transaction marked for rollback-only

     */
    @Test
    public void testConcurrentAccessVersion() throws InterruptedException {
        //Prepare data
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        HREmployeeOptimisticManager alice = new HREmployeeOptimisticManager(entityManagerFactory.createEntityManager(), employee.getId(), 10, 1000);
        HREmployeeOptimisticManager bob = new HREmployeeOptimisticManager(entityManagerFactory.createEntityManager(), employee.getId(), 1, 3000);
        new Thread(alice).start();
        new Thread(bob).start();
        Thread.sleep(4000);

        entityManager.refresh(employee);
        Assert.assertEquals(10, employee.getVacationDays());
    }

}