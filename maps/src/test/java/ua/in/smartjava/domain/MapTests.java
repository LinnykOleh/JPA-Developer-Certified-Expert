package ua.in.smartjava.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import ua.in.smartjava.basic.Clerk;
import ua.in.smartjava.basic.Department;
import ua.in.smartjava.basic.Employee;
import ua.in.smartjava.basic.EmployeeEnum;
import ua.in.smartjava.basic.PhoneType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@ActiveProfiles("mysql")
public class MapTests {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    /**
     * insert into Employee (name) values ('BOB')
     * insert into EMP_PHONES (Employee_id, PHONE_TYPE, PHONE_NUM) values (1, 'WORK', '123')
     * insert into EMP_PHONES (Employee_id, PHONE_TYPE, PHONE_NUM) values (1, 'HOME', '222')
     * commit
     */
    @Test
    public void testBasicTypesMap() {
        // Given
        Map<String, String> phones = new HashMap<>();
        phones.put("WORK", "123");
        phones.put("HOME", "222");

        Employee bob = Employee.builder()
                .name("BOB")
                .phoneNumbers(phones).build();

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(bob);
        entityManager.getTransaction().commit();

        // Then
    }

    /**
     * insert into EmployeeEnum (name) values ('BOB')
     * insert into EMP_ENUM_PHONES (EmployeeEnum_id, PHONE_TYPE, PHONE_NUM) values (1, 'HOME', '222')
     * insert into EMP_ENUM_PHONES (EmployeeEnum_id, PHONE_TYPE, PHONE_NUM) values (1, 'WORK', '123')
     * commit
     */
    @Test
    public void testEnumBasicMap() {
        // Given
        Map<PhoneType, String> phones = new HashMap<>();
        phones.put(PhoneType.WORK, "123");
        phones.put(PhoneType.HOME, "222");

        EmployeeEnum bob = EmployeeEnum.builder()
                .name("BOB")
                .phoneNumbers(phones).build();

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(bob);
        entityManager.getTransaction().commit();

        // Then
    }

    /**
     * insert into Department (name) values ('TOP')
     * insert into Clerk (department_id, name) values (1, 'Alice')
     * insert into Clerk (department_id, name) values (1, 'Bob')
     * update Clerk set clerksByDepartment_KEY='TOP' where id=1
     * update Clerk set clerksByDepartment_KEY='LOW' where id=2
     */
    @Test
    public void testBasicAndEntity() {
        // Given
        Department department = Department.builder().name("TOP")
                .build();
        Clerk alice = Clerk.builder().name("Alice").department(department).build();
        Clerk bob = Clerk.builder().name("Bob").department(department).build();

        Map<String, Clerk> map = new HashMap<>();
        map.put("TOP", alice);
        map.put("LOW", bob);
        department.setClerksByDepartment(map);
        entityManager.getTransaction().begin();
        entityManager.persist(department);
        entityManager.getTransaction().commit();

        // When
        Department loadedDepartment = entityManager.find(Department.class, department.getId());
        Clerk top = loadedDepartment.getClerksByDepartment().get("TOP");
        Clerk low = loadedDepartment.getClerksByDepartment().get("LOW");

        // Then
        Assert.assertEquals("Alice", top.getName());
        Assert.assertEquals("Bob", low.getName());
    }


}