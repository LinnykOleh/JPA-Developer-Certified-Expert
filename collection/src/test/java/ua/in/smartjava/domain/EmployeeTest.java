package ua.in.smartjava.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.in.smartjava.bidirectional.one_to_one.StudentOneToOne;
import ua.in.smartjava.bidirectional.one_to_one.TicketOneToOne;
import ua.in.smartjava.unidirectional.many_to_one.Department;
import ua.in.smartjava.unidirectional.many_to_one.Employee;
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
     * Table            Table
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
    public void testManyToOne() {
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

}