package ua.in.smartjava.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;
import ua.in.smartjava.domain.access.FieldAccess;
import ua.in.smartjava.domain.access.PropertyAccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@ActiveProfiles("mysql")
public class AccessTest {

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
    public void testFieldAccess() {
        //Prepare data
        entityManager.getTransaction().begin();
        FieldAccess fieldAccessedEntity = new FieldAccess("TEST");
        entityManager.persist(fieldAccessedEntity);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPropertyAccess() {
        // Given
        entityManager.getTransaction().begin();
        PropertyAccess propertyAccess = new PropertyAccess();
        propertyAccess.setPhoneFromDb("1234567890");
        entityManager.persist(propertyAccess);
        entityManager.getTransaction().commit();

        // When-Then
        Assert.assertEquals("+3801234567890", propertyAccess.getPhoneFromDb());
    }

}