package ua.in.smartjava.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.in.smartjava.bidirectional.one_to_one.StudentOneToOne;
import ua.in.smartjava.bidirectional.one_to_one.TicketOneToOne;
import ua.in.smartjava.unidirectional.many_to_one.Department;
import ua.in.smartjava.unidirectional.many_to_one.Employee;
import ua.in.smartjava.unidirectional.one_to_many.Car;
import ua.in.smartjava.unidirectional.one_to_many.Wheel;
import ua.in.smartjava.unidirectional.one_to_one.Student;
import ua.in.smartjava.unidirectional.one_to_one.Ticket;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("mysql")
public class EmployeeTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * insert into TicketOneToOne (num) values (13)
     * insert into StudentOneToOne (name, ticket_id) values ('ST1', 1)
     * commit
     *
     * Room            Room
     * TicketOneToOne           StudentOneToOne
     *                  ticket_id FK
     *
     *  target           owner
     */
    @Test
    public void testOneToOneUnidirectional() {
        Ticket ticket = Ticket.builder().num(13).build();
        Student student = Student.builder().ticket(ticket).name("ST1").build();
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    /**
     * insert into TICKET_1_1_BI (num) values (13)
     * insert into STUDENT_1_1_BI (name, ticket_id) values ('ST1', 1)
     */
    @Test
    public void testOneToOneBidirectional() {
        TicketOneToOne ticket = TicketOneToOne.builder().num(13).build();
        StudentOneToOne student = StudentOneToOne.builder().ticket(ticket).name("ST1").build();
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    /*
    insert into Department (name) values ('DEPT')
    insert into Employee (DEPT_ID, name) values (2, 'FIRST')
    insert into Employee (DEPT_ID, name) values (2, 'SECOND')
     */
    @Test
    public void testManyToOneUnidirectional() {
        // Given
        Department dept = Department.builder().name("DEPT").build();
        Employee first = Employee.builder().name("FIRST").department(dept).build();
        Employee second = Employee.builder().name("SECOND").department(dept).build();

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.getTransaction().commit();
        // Then
    }

    /**
     * insert into DEP_1_TO_MANY_BI (name) values ('DEPT')
     * insert into EMP_1_TO_MANY_BI (DEPT_ID, name) values (1, 'FIRST')
     * insert into EMP_1_TO_MANY_BI (DEPT_ID, name) values (1, 'SECOND')
     */
    @Test
    public void testManyToOneBidirectional() {
        // Given
        ua.in.smartjava.bidirectional.one_to_many.Department dept = ua.in.smartjava.bidirectional.one_to_many.Department.builder().name("DEPT").build();
        ua.in.smartjava.bidirectional.one_to_many.Employee first = ua.in.smartjava.bidirectional.one_to_many.Employee.builder().name("FIRST").department(dept).build();
        ua.in.smartjava.bidirectional.one_to_many.Employee second = ua.in.smartjava.bidirectional.one_to_many.Employee.builder().name("SECOND").department(dept).build();
        dept.setEmployees(Arrays.asList(first, second));

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.getTransaction().commit();
        // Then
    }

    /**
     * insert into Car (name) values ('bmw')
     * insert into Wheel values ( )
     * insert into Wheel values ( )
     * insert into CAR_WHEEL (CAR_ID, EH_ID) values (2, 3)
     * insert into CAR_WHEEL (CAR_ID, EH_ID) values (2, 4)
     */
    @Test
    public void testOneToManyUnidirectional() {
        // Given
        Wheel left = Wheel.builder().build();
        Wheel right = Wheel.builder().build();

        Car bmw = Car.builder().name("bmw").wheels(Arrays.asList(left, right)).build();

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(bmw);
        entityManager.getTransaction().commit();

        // Then
    }

}