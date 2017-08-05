package ua.in.smartjava.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ua.in.smartjava.domain.Address;
import ua.in.smartjava.domain.Employee;
import ua.in.smartjava.queries.EmpMenu;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueriesTest extends BaseJpaTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(QueriesTest.class);
    private static final String NAME = "test1";

    @Before
    public void init() {
        entityManager.createQuery("DELETE from Employee");
        entityManager.getTransaction().begin();
        Employee employee = new Employee(1L);
        Address address = new Address();
        address.setStreet("Himichna");
        employee.setAddress(address);
        employee.setName(NAME);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    @After
    public void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE from Employee");
        entityManager.getTransaction().commit();
    }

    @Test
    public void testProjection() {
        // Given-When
        Object singleResult = entityManager.createQuery("SELECT e.name, e.address.street " +
                "FROM Employee e JOIN e.address p ").getSingleResult();

        // Then
        LOGGER.error(singleResult.toString());
    }



    @Test
    public void testJoins() {
        // Given-When
        String QUERY = "SELECT e FROM Employee e JOIN e.address a";

        Employee emp = entityManager.createQuery(QUERY, Employee.class)
                .getSingleResult();

        // Then
        assertEquals(NAME, emp.getName());
    }

    @Test
    public void testInnerJoinNoPhones() {
        // Given-When
        String QUERY = "SELECT e FROM Employee e JOIN e.phones p";

        List<Employee> resultList = entityManager.createQuery(QUERY, Employee.class)
                .getResultList();

        // Then
        assertEquals(0, resultList.size());
    }

    @Test(expected = NoResultException.class)
    public void testException() {
        // Given-When
        String QUERY = "SELECT e FROM Employee e JOIN e.phones p";

        Employee singleResult = entityManager.createQuery(QUERY, Employee.class)
                .getSingleResult();
    }

    @Test
    public void testSpResultType() {
        // Given-When
        EmpMenu singleResult = entityManager.createQuery("SELECT NEW ua.org.smartjava.queries.EmpMenu(e.name," +
                " e.address.street) " +
                "FROM Employee e JOIN e.address p ", EmpMenu.class).getSingleResult();

        // Then
        LOGGER.error(singleResult.toString());
    }


}