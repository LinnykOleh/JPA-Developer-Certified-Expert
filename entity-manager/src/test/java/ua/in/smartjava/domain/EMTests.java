package ua.in.smartjava.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EMTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Before
    public void tearDown() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE from Employee e");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    //  Persist
    @Test
    public void testPersist_new() {
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        entityManager.getTransaction().commit();
        // Then
        Assert.assertTrue(entityManager.contains(bob));
    }

    @Test
    public void testPersist_notAnEntity() {
        entityManager.getTransaction().begin();
        // Expect
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Unknown entity: java.lang.String");
        // Given
        String notAnEntity = "NOT_AN_ENTITY";
        // When
        entityManager.persist(notAnEntity);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPersist_managed() {
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        final int bobId = bob.getId();
        entityManager.persist(bob);
        entityManager.getTransaction().commit();
        Assert.assertEquals(bob.getId(), bobId);
    }

    @Test
    public void testPersist_newWithPKExsisting() {
        //Expect
//        JPA declares EntityExistsException but PersistenceException is thrown by Hibernate
//        exception.expect(EntityExistsException.class);
        exception.expect(PersistenceException.class);
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        Employee bobDuplicate = Employee.builder().name("Bob duplicate").id(bob.getId()).build();
        entityManager.persist(bobDuplicate);
        // Then
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPersist_detached() {
        //Expect
//        This is strange too no EntityExistsException is thrown !!!
//        exception.expect(EntityExistsException.class);
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        entityManager.detach(bob);
        // Then
        entityManager.getTransaction().commit();
    }

    @Test
    public void testPersist_removed() {
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        final int id = bob.getId();
        entityManager.remove(bob);
        Employee alice = Employee.builder().name("Alice").build();
        entityManager.persist(alice);
        entityManager.persist(bob);

        Assert.assertTrue(entityManager.contains(bob));
        // Then
        entityManager.getTransaction().commit();
    }

//    Contains
    @Test
    public void testContains() {
        entityManager.getTransaction().begin();
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.persist(bob);
        // Then
        Assert.assertTrue(entityManager.contains(bob));
        entityManager.getTransaction().commit();
    }

    @Test
    public void testContains_notEntity() {
        exception.expect(IllegalArgumentException.class);
        entityManager.getTransaction().begin();
        // Given
        // When
        entityManager.persist("NOT_ENTITY");
        // Then
        entityManager.getTransaction().commit();
    }
}