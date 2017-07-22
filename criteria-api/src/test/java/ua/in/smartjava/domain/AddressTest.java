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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Test
    public void getStreet() throws Exception {
        entityManager.getTransaction().begin();

        Address bestStreet = Address.builder().street("BestStreet").build();
        entityManager.persist(bestStreet);

        entityManager.getTransaction().commit();

        Assert.assertEquals("BestStreet", bestStreet.getStreet());
    }

}