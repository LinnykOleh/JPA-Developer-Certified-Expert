package ua.in.smartjava.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import ua.in.smartjava.validationGroups.Personal;
import ua.in.smartjava.validationGroups.StreetGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ValidationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Autowired
    private Validator validator;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE from Employee e");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void testValidationOnPersist() {
        // Given
        Employee build = Employee.builder().district("test").build();

        // When
        entityManager.persist(build);

        // Then
        exception.expect(ConstraintViolationException.class);
        exception.expectMessage("Street can not be null");
    }

    @Test(expected = RollbackException.class)
    public void testValidationOnUpdate() {
        // Given
        entityManager.getTransaction().begin();
        Employee build = Employee.builder().district("test").street("BackerStreet").build();
        entityManager.persist(build);
        entityManager.getTransaction().commit();

        // When
        entityManager.getTransaction().begin();
        build.setStreet(null);
        entityManager.persist(build);
        entityManager.getTransaction().commit();

        // Then
        exception.expectCause(is(ConstraintViolationException.class));

        exception.expectCause(new StreetExceptionMatcher());
    }

    @Test
    public void testValidatePersonalGroup() {
        // Given
        final Employee employee = Employee.builder().district("13region").build();

        // When-Then
        assertThat(validator.validate(employee, Personal.class)).extracting("message")
                .containsOnly("Street can not be null", "size must be between 1 and 2");
    }

    @Test
    public void testDefaultGroup() {
        // Given
        final Employee employee = Employee.builder().build();

        // When-Then
        assertThat(validator.validate(employee, StreetGroup.class)).extracting("message")
                .containsOnly("The street does not match");
    }

    @Test
    public void testStreetConstraint() {
        // Given
        final Employee employee = Employee.builder().build();

        // When-Then
        assertThat(validator.validate(employee)).extracting("message")
                .containsOnly("Street can not be null");
    }


}