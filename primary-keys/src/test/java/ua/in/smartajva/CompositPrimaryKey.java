package ua.in.smartajva;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

/**
 * Hibernate:
 * create table manufa (
 * country varchar(255) not null,
 * id integer not null,
 * name varchar(255),
 * primary key (country, id))
 *
 * Hibernate:
 * create table manufactor (
 *  country varchar(255) not null,
 *  manuf_id integer not null,
 *  name varchar(255),
 *  primary key (country, manuf_id))
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompositPrimaryKey {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testIdClass() {
        // Given
        entityManager.getTransaction().begin();
        entityManager.persist(new Manufactor("NAME", "COUNTRY"));
        entityManager.getTransaction().commit();

        // When
        ManufactorId manufactorId = new ManufactorId("COUNTRY", 0);
        Manufactor manufactor = entityManager.find(Manufactor.class, manufactorId);

        // Then
        assertEquals("NAME", manufactor.getName());
    }

    @Test
    public void testEmbeddedIdClass() {
        // Given
        entityManager.getTransaction().begin();
        entityManager.persist(new Manufa(new ManufactorEmbeddableId("COUNTRY", 0), "NAME"));
        entityManager.getTransaction().commit();

        // When
        Manufa country = entityManager.find(Manufa.class, new ManufactorEmbeddableId("COUNTRY", 0));

        // Then
        assertEquals("NAME", country.getName());
    }
}