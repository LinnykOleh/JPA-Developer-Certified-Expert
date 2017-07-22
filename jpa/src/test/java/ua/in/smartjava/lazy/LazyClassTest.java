package ua.in.smartjava.lazy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ua.in.smartjava.base.BaseJpaTest;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LazyClassTest extends BaseJpaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LazyClassTest.class);
    private static final String NAME = "Name";

    @Before
    public void init() {
        entityManager.getTransaction().begin();
        LazyClass lazyClass = new LazyClass();
        lazyClass.setName("Jack Richard");
        lazyClass.setPosition("veteran");
        entityManager.persist(lazyClass);
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();
    }

    @Test
    public void test_ContextLoads() {
        LOGGER.info("start");
        LazyClass lazyClass = entityManager.find(LazyClass.class, 0L);
        assertEquals("Jack Richard", lazyClass.getName());
    }
}