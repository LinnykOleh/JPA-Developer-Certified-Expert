package ua.in.smartjava.base;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.in.smartjava.repositories.MapBaseRepository;

@ActiveProfiles(value = "mysql")
public class BaseJpaTest {
    protected EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    MapBaseRepository mapBaseRepository;

    @Before
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        mapBaseRepository.deleteAll();
    }
}
