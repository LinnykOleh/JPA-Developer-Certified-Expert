package ua.org.smartjava.primaryKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.TypedQuery;

import ua.org.smartjava.BaseJpaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpTableTest extends BaseJpaTest {

    private void createData(int count) {
        for (int i = 0; i < count; i++) {
            entityManager.getTransaction().begin();
            entityManager.persist(new EmpTable());
            entityManager.getTransaction().commit();
        }
    }
/*
    allocationSize = 10; when count of objects increase the allocationSize valueColumnName in generator
    table is incremented

    ID: 8     GEN_VAL: 1
    ID: 9     GEN_VAL: 2
    ID: 289   GEN_VAL: 29
    291 290   GEN_VAL: 30
*/

    @Test
    public void test() {
        final int entitiesCount = 300;
        createData(entitiesCount);
        TypedQuery<EmpTable> query = entityManager.createQuery("SELECT e FROM EmpTable e", EmpTable.class);
        List<EmpTable> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
    }

}