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
    }

}