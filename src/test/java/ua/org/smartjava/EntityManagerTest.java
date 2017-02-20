package ua.org.smartjava;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.org.smartjava.domain.Address;
import ua.org.smartjava.domain.Employee;
import ua.org.smartjava.domain.Phone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerTest extends BaseJpaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerTest.class);
    private static final String NAME = "Name";

    @Before
    public void init() {
//        entityManager.getTransaction().begin();
        Employee employee = new Employee(1L);
        employee.setName(NAME);
        entityManager.persist(employee);
//        entityManager.getTransaction().commit();
    }

    @Test
    public void test_ContextLoads() {
        LOGGER.info("start");
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

    @Test
    public void testOneToOne() {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L);
        Address address = new Address();
        employee.setAddress(address);
        entityManager.persist(address);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        employee = entityManager.find(Employee.class, 1L);

        assertNotNull(employee.getAddress());
        assertTrue(employee.getAddress().getId() == 0);
    }

    @Test
//    @Ignore
    public void testManyToMany() {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L);
        Stream<Phone> stream = Stream.generate(Phone::new);
        List<Phone> phones = stream.limit(5)
                .peek(p -> p.setModel("nokia"))
                .peek(phone -> entityManager.persist(phone))
                .collect(Collectors.toList());
        employee.setPhones(phones);
//        for (Phone phone : phones) {
//            entityManager.persist(phone);
//        }
        entityManager.merge(employee);
        entityManager.getTransaction().commit();

        Employee employee1 = entityManager.find(Employee.class, 1L);
        assertEquals(5, employee1.getPhones().size());
    }

}