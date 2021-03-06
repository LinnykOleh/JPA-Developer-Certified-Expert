package ua.in.smartjava;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import ua.in.smartjava.domain.EmpMenu;
import ua.in.smartjava.domain.Employee;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpqlApplicationTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private final String NAME = "NAME";
    private long id;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE from Employee");
        query.executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee employee = Employee.builder().name(NAME).employmentDate(new Date(1)).build();
        entityManager.persist(Employee.builder().name("NOISE").build());
        entityManager.persist(employee);
        this.id = employee.getId();
        entityManager.getTransaction().commit();
    }

    @Test
    public void testNamedParamBinding() {
        // Given-When
        String query = "SELECT e.name FROM Employee e WHERE e.id = :empId";

        String name = entityManager.createQuery(query, String.class)
                .setParameter("empId", this.id)
                .getSingleResult();

        // Then
        assertEquals(NAME, name);
    }

    @Test
    public void testBindingParams() {
        // Given
        String query = "SELECT e.name FROM Employee e WHERE e.id = ?0";

        // When
        String name = entityManager.createQuery(query, String.class)
                .setParameter(0, this.id)
                .getSingleResult();

        // Then
        assertEquals(NAME, name);
    }

    @Test
    public void testNamedQueryFindId() {
        // Given
        String empName = entityManager.createNamedQuery("Employee.findEmployeeNameById", String.class)
                .setParameter("empId", this.id)
                .getSingleResult();
        // When-Then
        assertEquals(NAME, empName);
    }

    @Test
    public void testNamedQueryFindByName() {
        // Given
        Employee employee = entityManager.createNamedQuery("Employee.findEmployeeById", Employee.class)
                .setParameter("empId", this.id)
                .getSingleResult();

        // When-Then
        assertEquals(NAME, employee.getName());
    }

    /**
     * Dynamic registration of named-query
     */
    @Test
    public void testCreateDynamicNamedQuery() {
        // Given
        TypedQuery<Employee> typedQuery = entityManager.createQuery("SELECT e FROM Employee e",
                Employee.class);
        entityManagerFactory.addNamedQuery("Test.findAll", typedQuery);

        // When
        List<Employee> resultList = entityManager.createNamedQuery("Test.findAll")
                .getResultList();

        // Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void testNoResultsSingleResult() {
        // Expect
        exception.expect(NoResultException.class);
        // Given
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e from Employee e where e.id = ?1", Employee
                .class);

        // When
        Employee singleResult = query.setParameter(1, ++this.id)
                .getSingleResult();

        // Then
    }

    @Test
    public void testNoResultsResultList() {
        // Given
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e from Employee e where e.id = ?1", Employee
                .class);

        // When
        List<Employee> singleResult = query.setParameter(1, ++this.id)
                .getResultList();

        // Then
        assertEquals(0, singleResult.size());
    }

    @Test
    public void testDateParams() {
        // Given
        Employee employee = entityManager.createNamedQuery("Emloyee.findByDate", Employee.class)
                .setParameter("empDate", new Date(1), TemporalType.DATE)
                .getSingleResult();

        // When-Then
        assertEquals(NAME, employee.getName());
    }

    @Test
    public void testQueryObjectArray() {
        // Given
        Object[] object = (Object[]) entityManager.createQuery("select e.name, e.employmentDate from Employee e where e.id = :empId")
                .setParameter("empId", this.id)
                .getSingleResult();

        // When-Then
        assertEquals(NAME, object[0]);
        assertEquals(new Date(1), object[1]);
    }

    @Test
    public void testQueryNewObject() {
        // Given
        EmpMenu empMenu = entityManager.createQuery("SELECT new ua.in.smartjava.domain.EmpMenu(e.name, e.employmentDate) from Employee " +
                "e where e.id = ?1", EmpMenu.class)
                .setParameter(1, this.id)
                .getSingleResult();

        // When-Then
        assertEquals(NAME, empMenu.getName());
        assertEquals(new Date(1), empMenu.getEmploymentDate());
    }

    @Test
    public void testPagination() {
        // Given

        generateEmployees(10);
        List<Employee> resultList = entityManager.createQuery("SELECT e FROM Employee e WHERE e.name LIKE 'generated_%'", Employee.class)
//                starting from elementID
                .setFirstResult(2)
//                elements count
                .setMaxResults(2)
                .getResultList();

        // When-Then
        assertEquals(2, resultList.size());
        assertEquals("generated_2", resultList.get(0).getName());
        assertEquals("generated_3", resultList.get(1).getName());
    }

    private void generateEmployees(int count) {
        entityManager.getTransaction().begin();
        IntStream.range(0, count)
                .mapToObj(i -> Employee.builder().name("generated_" +i).employmentDate(new Date(1)).build())
                .peek(System.out::println)
                .forEach(entityManager::persist);
        entityManager.getTransaction().commit();
    }

}
