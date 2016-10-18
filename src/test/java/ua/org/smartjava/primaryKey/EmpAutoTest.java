package ua.org.smartjava.primaryKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.TypedQuery;

import ua.org.smartjava.BaseJpaTest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpAutoTest extends BaseJpaTest {

    private void createData(int count) {
        for (int i = 0; i < count; i++) {
            entityManager.getTransaction().begin();
            EmpAuto employee = new EmpAuto();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        }
    }

    @Test
    public void test() {
        final int entitiesCount = 10;
        createData(entitiesCount);
        TypedQuery<EmpAuto> query = entityManager.createQuery("SELECT e FROM EmpAuto e", EmpAuto.class);
        List<EmpAuto> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
    }

}