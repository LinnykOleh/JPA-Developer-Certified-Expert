package ua.in.smartjava.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ua.in.smartjava.domain.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTest extends BaseJpaTest{

    @Before
    public void init() {
        entityManager.getTransaction().begin();
        Employee employee = new Employee();
        employee.setName("Test");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    @Test
    public void test() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        criteriaBuilder.createTupleQuery();
        Root<Employee> emp = query.from(Employee.class);

        CriteriaQuery<Employee> where = query.select(emp)
                .where(criteriaBuilder.equal(emp.get("name"), "Test"));


        // When
        TypedQuery<Employee> query1 = entityManager.createQuery(where);
        query1.setParameter("name", "noame");

        // Then
    }

    @Test
    public void testRoot() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> from = query.from(Employee.class);

        query.select(from);
        TypedQuery<Employee> query1 = entityManager.createQuery(query);
        List<Employee> resultList = query1.getResultList();

    }
}
