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
public class PrimaryKeyTest extends BaseJpaTest {

    private void createDataTable(int count) {
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
    public void testTableSequence() {
        final int entitiesCount = 300;
        createDataTable(entitiesCount);
        TypedQuery<EmpTable> query = entityManager.createQuery("SELECT e FROM EmpTable e", EmpTable.class);
        List<EmpTable> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
        //TODO add query to get value from SELECT * FROM `jpa-certificate`.ID_GEN;
    }

    private void createDataAuto(int count) {
        for (int i = 0; i < count; i++) {
            entityManager.getTransaction().begin();
            EmpAuto employee = new EmpAuto();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        }
    }

    @Test
    public void testAutoGeneration() {
        final int entitiesCount = 10;
        createDataAuto(entitiesCount);
        TypedQuery<EmpAuto> query = entityManager.createQuery("SELECT e FROM EmpAuto e", EmpAuto.class);
        List<EmpAuto> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
    }

    @Test
    public void testSequence() {
//        not supported in MySql
//        Oracle
//        SQL Server 2012
//        PostgreSQL
//        DB2
//        HSQLDB
    }

}