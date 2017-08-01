package ua.in.smartajva;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.in.smartajva.joinTables.FirstYearEmployee;
import ua.in.smartajva.joinTables.SecondYearEmloyee;
import ua.in.smartajva.singleTable.Employee;
import ua.in.smartajva.tablePerConcreteClass.CompanyEmployee;
import ua.in.smartajva.tablePerConcreteClass.PartTimerEmployee;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("mysql")
public class InheritenceTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Hibernate:
     * create table employee (
     * dtype varchar(31) not null, ***THIS IS DISCRIMINATOR***
     * id integer generated by default as identity,
     * name varchar(255),
     * vacation integer,
     * salary integer,
     * primary key (id))
     */
    @Test
    public void testSingleTable() {
        // Given
        Employee bob = Employee.builder().name("Bob").build();
        // When
        entityManager.getTransaction().begin();
        entityManager.persist(bob);
        entityManager.getTransaction().commit();

        // Then
    }

    @Test
    public void testTransientClass() {
        // Given
        Employee bob = Employee.builder().name("Alice").build();
        // When
        entityManager.getTransaction().begin();
        entityManager.persist(bob);
        entityManager.getTransaction().commit();

        // Then
        Assert.assertTrue(bob.getCachedAge() > 0);
    }

    /*
    Join Tables not efficient. As more hierarchy - more joins will perform.

    Hibernate: create table emp (
        emp_type integer not null,
        id integer generated by default as identity,
        name varchar(255),
        primary key (id))

    Hibernate: create table first_year_employee (
        vacation integer not null,
        id integer not null,
        primary key (id))

    Hibernate: create table second_level_emloyee (
        salary integer not null,
        id integer not null,
        primary key (id))

     */
    @Test
    public void testJoinTable() {
        // Given
        FirstYearEmployee firstYearEmployee = new FirstYearEmployee();
        SecondYearEmloyee secondYearEmloyee = new SecondYearEmloyee();

        entityManager.getTransaction().begin();
        entityManager.persist(firstYearEmployee);
        entityManager.persist(secondYearEmloyee);
        entityManager.getTransaction().commit();
        // When

        // Then
    }

    /*
    Per Concrete Class:

//This table is not created if PER_CLASS_EMPLOYEE is abstract
    Hibernate: create table per_class_employee (
        id integer not null,
        name varchar(255),
        primary key (id))

    Hibernate: create table per_class_part (
        id integer not null,
        name varchar(255),
        time integer not null,
        primary key (id))

    Hibernate: create table second_year_emloyee (
        salary integer not null,
        id integer not null,
        primary key (id))
    */
    @Test
    public void testTablePerConcreteClass() {
        // Given
        CompanyEmployee companyEmployee = new CompanyEmployee();
        PartTimerEmployee partTimerEmployee = new PartTimerEmployee();

        entityManager.getTransaction().begin();
        entityManager.persist(companyEmployee);
        entityManager.persist(partTimerEmployee);
        entityManager.getTransaction().commit();
        // When

        // Then
    }

}