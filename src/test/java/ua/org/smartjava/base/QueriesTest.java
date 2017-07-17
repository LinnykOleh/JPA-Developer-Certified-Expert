package ua.org.smartjava.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.org.smartjava.domain.Address;
import ua.org.smartjava.domain.Employee;
import ua.org.smartjava.queries.EmpMenu;

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

    @Test
    public void testProjection() {
        // Given-When
        Object singleResult = entityManager.createQuery("SELECT e.name, e.address.street " +
                "FROM Employee e JOIN e.address p ").getSingleResult();

        // Then
        LOGGER.error(singleResult.toString());
    }

    @Test
    public void testParamBinding() {
        // Given-When
        String QUERY = "SELECT e.name FROM Employee e WHERE e.id = :empId";

        String name = entityManager.createQuery(QUERY, String.class)
                .setParameter("empId", 1L)
                .getSingleResult();

        // Then
        assertEquals(NAME, name);
    }

    @Test
    public void testParamBindingNumber() {
        // Given-When
        String QUERY = "SELECT e.name FROM Employee e WHERE e.id = ?1";

        String name = entityManager.createQuery(QUERY, String.class)
                .setParameter(1, 1L)
                .getSingleResult();

        // Then
        assertEquals(NAME, name);
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

    @Test
    public void test() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> employeeList = query.getResultList();
        assertEquals(1, employeeList.size());
    }

}