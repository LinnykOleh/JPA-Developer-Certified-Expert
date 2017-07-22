package ua.in.smartjava.primaryKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.TypedQuery;

import ua.in.smartjava.base.TestUtils;
import ua.in.smartjava.base.BaseJpaTest;
import ua.in.smartjava.domain.primaryKey.EmpAuto;
import ua.in.smartjava.domain.primaryKey.EmpSequence;
import ua.in.smartjava.domain.primaryKey.EmpTable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(
        basePackages = "ua.org.smartjava.domain.primaryKey"
        )
public class PrimaryKeyTest extends BaseJpaTest {

    @Autowired
    private TestUtils testUtils;

    @Test
    public void testAutoGeneration() throws InstantiationException, IllegalAccessException {
        final int entitiesCount = 10;
        testUtils.preparePersistenceClasses(EmpAuto.class, entitiesCount);
        TypedQuery<EmpAuto> query = entityManager.createQuery("SELECT e FROM EmpAuto e", EmpAuto.class);
        List<EmpAuto> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
    }

/*
    allocationSize = 10; when count of objects increase the allocationSize valueColumnName in generator
    table is incremented

    ID: 1     GEN_VAL: 1
    ID: 2     GEN_VAL: 1
    ID: 10    GEN_VAL: 2
    ID: 300   GEN_VAL: 31
*/

    @Test
    public void testTableGenerator() throws InstantiationException, IllegalAccessException {
        final int entitiesCount = 30;
        testUtils.preparePersistenceClasses(EmpTable.class, entitiesCount);
        TypedQuery<EmpTable> query = entityManager.createQuery("SELECT e FROM EmpTable e", EmpTable.class);
        List<EmpTable> employeeList = query.getResultList();
        assertEquals(entitiesCount, employeeList.size());
        assertTrue(employeeList.get(entitiesCount - 1).getId() == entitiesCount);
    }

    @Test
    public void testSequence() throws InstantiationException, IllegalAccessException {
//        not supported in MySql
//        Oracle
//        SQL Server 2012
//        PostgreSQL
//        DB2
//        HSQLDB
        testUtils.preparePersistenceClasses(EmpSequence.class, 10);
    }

}