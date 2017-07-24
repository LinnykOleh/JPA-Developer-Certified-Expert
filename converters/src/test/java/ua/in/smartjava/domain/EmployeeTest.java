package ua.in.smartjava.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Hibernate: insert into employee (id, name, bonded, top_skilled) values (null, ?, ?, ?)
     * Hibernate: insert into employee_secured (employee_id, secured) values (?, ?)
     * Hibernate: insert into employee_secured (employee_id, secured) values (?, ?)
     */
    @Test
    public void test() {
        // Given
        Employee john = Employee.builder()
                .name("John")
                .topSkilled(true)
                .securityInfo(new SecurityInfo(true))
                .secured(Arrays.asList(true, false))
                .build();
        // When
        entityManager.getTransaction().begin();
        entityManager.persist(john);
        entityManager.getTransaction().commit();

        // Then
    }

}