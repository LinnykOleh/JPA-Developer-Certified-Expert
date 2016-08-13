package ua.org.smartjava;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.org.smartjava.domain.Employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerTest {
    private static final String NAME = "Name";
    private EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
        Employee employee = new Employee(1L);
        employee.setName(NAME);
        entityManager.persist(employee);
//        entityManager.getTransaction().commit();
    }

    @Test
    public void test_ContextLoads() {
        Employee employee = entityManager.find(Employee.class, 1L);
        assertEquals(employee.getId(), 1L);
        assertEquals(employee.getName(), NAME);
    }

    @Test
    public void test_NullOnNoExist() {
        assertNull(entityManager.find(Employee.class, 2L));
    }

    @Test
    public void test_RemoveNoPersistant() {
        entityManager.remove(new Employee(3L));
    }

    @Test
    public void test_Remove_Persistant() {
        Employee employee = entityManager.find(Employee.class, 1L);
        entityManager.remove(employee);
        assertTrue(employee != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_RemoveNull() {
        entityManager.remove(null);
    }

    @Test
    public void test_SimpleUpdate() {
        Employee employee1 = entityManager.find(Employee.class, 1L);
        employee1.setName("TEST");
        Employee employee2 = entityManager.find(Employee.class, 1L);
        assertEquals(employee2.getName(), "TEST");
    }

}