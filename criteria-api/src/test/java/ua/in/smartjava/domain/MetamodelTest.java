package ua.in.smartjava.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MetamodelTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Test
    public void test() {
        // Given
        Metamodel metamodel = entityManager.getMetamodel();
        metamodel.getManagedTypes().forEach(type -> System.err.println(String.valueOf(type)));

        // When
        EntityType<Address> entityType = metamodel.entity(Address.class);

        // Then
        assertEquals(Type.PersistenceType.ENTITY, entityType.getPersistenceType());
        assertEquals(Address.class, entityType.getJavaType());

        Set<Attribute<? super Address, ?>> attributes = entityType.getAttributes();
        assertEquals(3, attributes.size());
        for (Attribute attribute : attributes) {
            log.error(String.valueOf(attribute.getJavaType()));
        }
    }
}
