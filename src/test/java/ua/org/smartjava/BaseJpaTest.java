package ua.org.smartjava;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class BaseJpaTest {
    protected EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Before
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }
}
