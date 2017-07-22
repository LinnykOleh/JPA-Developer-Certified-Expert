package ua.in.smartjava.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MetamodelTest {

    @Autowired
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

        // When
        EntityType<Address> entityType = metamodel.entity(Address.class);

        // Then
        Assert.assertEquals(Type.PersistenceType.ENTITY, entityType.getPersistenceType());
        log.error(String.valueOf(entityType.getAttributes()));
        Assert.assertEquals(Address.class, entityType.getJavaType());
    }
}
