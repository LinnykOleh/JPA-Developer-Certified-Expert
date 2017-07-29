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
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LocksTest {

    private static final String STREET = "Baker Street";
    private static final String CITY = "London";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @After
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
Query	select employee0_.id as id1_1_0_, employee0_.district as district2_1_0_, employee0_.street as street3_1_0_, employee0_.vacationDays as vacation4_1_0_ from Employee employee0_ where employee0_.id=1
Query	select employee0_.id as id1_1_0_, employee0_.district as district2_1_0_, employee0_.street as street3_1_0_, employee0_.vacationDays as vacation4_1_0_ from Employee employee0_ where employee0_.id=1
Query	update Employee set district='London', street='Baker Street', vacationDays=10 where id=1
Query	commit

Query	update Employee set district='London', street='Baker Street', vacationDays=19 where id=1
Query	commit
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
Query	select employeeop0_.id as id1_2_0_, employeeop0_.district as district2_2_0_, employeeop0_.street as street3_2_0_, employeeop0_.vacationDays as vacation4_2_0_, employeeop0_.version as version5_2_0_ from EmployeeOptimistic employeeop0_ where employeeop0_.id=1
Query	select employeeop0_.id as id1_2_0_, employeeop0_.district as district2_2_0_, employeeop0_.street as street3_2_0_, employeeop0_.vacationDays as vacation4_2_0_, employeeop0_.version as version5_2_0_ from EmployeeOptimistic employeeop0_ where employeeop0_.id=1
Query	update EmployeeOptimistic set district='London', street='Baker Street', vacationDays=10, version=1 where id=1 and version=0
Query	commit

Query	update EmployeeOptimistic set district='London', street='Baker Street', vacationDays=19, version=1 where id=1 and version=0
Query	rollback
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

    /**
     *
     * Make sure that you are reading the values from objects that are not updated in parallel
     */
    @Test(expected = RollbackException.class)
    public void testOptimisticLockFail() throws InterruptedException {
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = entityManager.find(EmployeeOptimistic.class, employee.getId());
        executeParallelDistrictUpdate(employee);
        Thread.sleep(2000);

        entityManager.lock(employee, LockModeType.OPTIMISTIC);
/*        select version from EmployeeOptimistic where id =1
        the version has changed - fail the TX
 */
        entityManager.getTransaction().commit();
    }

    @Test
    public void testOptimisticLock() throws InterruptedException {
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = entityManager.find(EmployeeOptimistic.class, employee.getId());

        executeParallelDistrictUpdate(employee);

        entityManager.lock(employee, LockModeType.OPTIMISTIC);
        entityManager.getTransaction().commit();
    }

    private void executeParallelDistrictUpdate(EmployeeOptimistic employee) {
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        new Thread(() -> {
            entityManager2.getTransaction().begin();
            EmployeeOptimistic loadedEmployee = entityManager2.find(EmployeeOptimistic.class, employee.getId());
            loadedEmployee.setDistrict("NONE");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            entityManager2.getTransaction().commit();
        }).start();
    }

    @Test
    public void testOptimisticForceIncrementLock() throws InterruptedException {
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = entityManager.find(EmployeeOptimistic.class, employee.getId());
        final int primaryVersion = employee.getVersion();
        entityManager.lock(employee, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
//        Thread.sleep(2000);
/*
        Generates query select for update
2017-07-25T15:34:07.686628Z	  218 Query	update EmployeeOptimistic set version=1 where id=1 and version=0
2017-07-25T15:34:07.688387Z	  218 Query	commit
    Neither there was write operation or not OPTIMISTIC_FORCE_INCREMENT updates the Version

    If there was update of the state of Employee entity - them 2 version updates are performed:
2017-07-25T18:29:56.182831Z	  348 Query	update EmployeeOptimistic set district='DD', street='Baker Street', vacationDays=20, version=1 where id=1 and version=0
2017-07-25T18:29:56.184905Z	  348 Query	update EmployeeOptimistic set version=2 where id=1 and version=1
2017-07-25T18:29:56.186091Z	  348 Query	commit
 */
        entityManager.getTransaction().commit();
        Assert.assertTrue(primaryVersion < employee.getVersion());
    }

    @Test(expected = RollbackException.class)
    public void testOptimisticForceIncrementLockFail() throws InterruptedException {
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = entityManager.find(EmployeeOptimistic.class, employee.getId());
        final int primaryVersion = employee.getVersion();
        entityManager.lock(employee, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        executeParallelDistrictUpdate(employee);
        Thread.sleep(2000);
/*
        Generates query select for update
    update EmployeeOptimistic set version=1 where id=1 and version=0
    2017-07-25T15:32:38.360676Z	  208 Query	rollback
    because another TX already updated version
 */
        entityManager.getTransaction().commit();
        Assert.assertTrue(primaryVersion < employee.getVersion());
    }

    /**
     * PESSIMISTIC_WRITE this is exclusive lock
     */
    @Test
    public void testPessimisticWriteLocking() {
        entityManager.getTransaction().begin();
        Employee employee = Employee.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee loaded = entityManager.find(Employee.class, employee.getId());
        entityManager.lock(loaded, LockModeType.PESSIMISTIC_WRITE);
/*
        Generates query select for update
2017-07-25T18:14:03.781911Z	  298 Query	select id from Employee where id =1 for update
2017-07-25T18:14:03.789566Z	  298 Query	update Employee set district='NONE', street='Baker Street', vacationDays=20 where id=1
2017-07-25T18:14:03.790893Z	  298 Query	commit
*/
        loaded.setDistrict("NONE");
        entityManager.getTransaction().commit();
    }

    /**
     * PESSIMISTIC_READ this is shared lock
     */
    @Test
    public void testPessimisticReadLocking() {
        entityManager.getTransaction().begin();
        Employee employee = Employee.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee loaded = entityManager.find(Employee.class, employee.getId());
        entityManager.lock(loaded, LockModeType.PESSIMISTIC_READ);
/*
        Generates query select for update
2017-07-25T18:12:52.144539Z	  288 Query	select id from Employee where id =1 lock in share mode
2017-07-25T18:12:52.152284Z	  288 Query	update Employee set district='NONE', street='Baker Street', vacationDays=20 where id=1
2017-07-25T18:12:52.153792Z	  288 Query	commit
*/
        loaded.setDistrict("NONE");
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPessimisticForceIncrementLocking() {
        entityManager.getTransaction().begin();
        EmployeeOptimistic employee = EmployeeOptimistic.builder().street(STREET).district(CITY).vacationDays(20)
                .build();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        EmployeeOptimistic loaded = entityManager.find(EmployeeOptimistic.class, employee.getId());
        entityManager.lock(loaded, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
/*
        Generates query select for update
2017-07-25T18:17:54.478363Z	  338 Query	update EmployeeOptimistic set version=1 where id=1 and version=0
2017-07-25T18:17:54.491297Z	  338 Query	update EmployeeOptimistic set district='NONE', street='Baker Street', vacationDays=20, version=2 where id=1 and version=1
2017-07-25T18:17:54.492533Z	  338 Query	commit
*/
        loaded.setDistrict("NONE");
        entityManager.getTransaction().commit();
    }

}