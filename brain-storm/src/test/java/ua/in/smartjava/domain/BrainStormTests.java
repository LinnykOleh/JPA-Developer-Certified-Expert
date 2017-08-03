package ua.in.smartjava.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("mysql")
public class BrainStormTests {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void test() {
        Department dept = Department.builder().name("DEPT").build();
        Employee first = Employee.builder().name("FIRST").department(dept).build();
        Employee second = Employee.builder().name("SECOND").department(dept).build();

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(dept);
        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.remove(first);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPersistenceUtils() {
        Department dept = Department.builder().name("DEPT").build();
        entityManager.getTransaction().begin();
        entityManager.persist(dept);
        entityManager.getTransaction().commit();
        Assert.assertTrue(Persistence.getPersistenceUtil().isLoaded(dept));
    }

}