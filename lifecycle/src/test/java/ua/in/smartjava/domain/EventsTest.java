package ua.in.smartjava.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventsTest {

    @Autowired
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

    @Test
    public void testLifeCycleWithEM() {
        //Prepare data
        entityManager.getTransaction().begin();
        entityManager.persist(Employee.builder().street("Baker Street").district("London").build());
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L);
        employee.setStreet("NONE");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

//        Query deleteQuery = entityManager.createNativeQuery("DELETE FROM Employee");
//        deleteQuery.executeUpdate();
        entityManager.remove(employee);
        entityManager.getTransaction().commit();
    }

    /**
     * When executing with nativeQuery or Query no lifecycle callback are done
     */
    @Test
    public void testLifeCycleWithJPQL() {
        //Prepare data
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("INSERT INTO Employee (id, street, district) VALUES (0, ?, ?)");
        query.setParameter(1, "Baker Street");
        query.setParameter(2, "London");
        query.executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query updateQuery = entityManager.createQuery("update Employee e SET e.street = 'NONE' WHERE e.id = 0");
        updateQuery.executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query deleteQuery = entityManager.createNativeQuery("DELETE FROM Employee");
        deleteQuery.executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * PrePersist Employee, 0
     Hibernate: insert into employee (id, district, street) values (null, ?, ?)
     Listener on {} is invokedBaker Street
     PostPersist Employee, 1
     PreRemove Employee, 1
     Hibernate: delete from employee where id=?
     PostRemove Employee, 1

     Due to this happens in one TX - we can not rely on Pre- Post update calls should be made.
     */
    @Test
    public void testLifeCycleInOneTx() {
        entityManager.getTransaction().begin();
        Employee london = Employee.builder().street("Baker Street").district("London").build();
        entityManager.persist(london);
        Employee employee = entityManager.find(Employee.class, london.getId());
        employee.setStreet("NONE");
        entityManager.remove(employee);
        entityManager.getTransaction().commit();
    }

    /**
     *  PrePersist Employee, 0
     Hibernate: insert into employee (id, district, street) values (null, ?, ?)
     PostPersist Employee, 1
     PrePersist Cars, 0
     Hibernate: insert into cars (id, name) values (null, ?)
     PostPersist Cars, 1
     PrePersist Cars, 0
     Hibernate: insert into cars (id, name) values (null, ?)
     PostPersist Cars, 2
     Hibernate: insert into employee_cars (employee_id, cars_id) values (?, ?)
     Hibernate: insert into employee_cars (employee_id, cars_id) values (?, ?)
     PreUpdate Employee, 1
     Hibernate: update employee set district=?, street=? where id=?
     PostUpdate Employee, 1
     PreRemove Employee, 1
     PreRemove Cars, 1
     PreRemove Cars, 2
     Hibernate: delete from employee_cars where employee_id=?
     Hibernate: delete from cars where id=?
     PostRemove Cars, 1
     Hibernate: delete from cars where id=?
     PostRemove Cars, 2
     Hibernate: delete from employee where id=?
     PostRemove Employee, 1
     */
    @Test
    public void testLifeCycleRelationWithEM() {
        //Prepare data
        entityManager.getTransaction().begin();
        Employee london = Employee.builder().street("Baker Street").district("London").build();
        london.setCars(Arrays.asList(
                Cars.builder().name("BMW").build(),
                Cars.builder().name("Honda").build()
        ));
        entityManager.persist(london);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, london.getId());
        employee.setStreet("NONE");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.remove(employee);
        entityManager.getTransaction().commit();
    }

    /**
     * LifeCycle event are not triggered due to they are in unmapped super class of CarBroken
     * @MappedSuperclass or @Entity annotation must be present
     */
    @Test
    public void testUnMappedSuperClass() {
        entityManager.getTransaction().begin();
        entityManager.persist(new CarBroken());
        entityManager.getTransaction().commit();
    }
}