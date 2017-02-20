package ua.org.smartjava;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ActiveProfiles(value = "mysql")
public class BaseJpaTest {
    protected EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Before
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }
}
