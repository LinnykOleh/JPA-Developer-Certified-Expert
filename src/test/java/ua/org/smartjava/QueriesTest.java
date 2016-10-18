package ua.org.smartjava;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ua.org.smartjava.domain.Employee;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueriesTest{
    private static final String NAME = "test1";
    protected EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Before
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Before
    public void init() {
        entityManager.createQuery("DELETE from Employee");
        entityManager.getTransaction().begin();
        Employee employee = new Employee(1L);
        employee.setName(NAME);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    @Test
    public void test() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> employeeList = query.getResultList();
        assertEquals(1, employeeList.size());
    }

}