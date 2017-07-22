package ua.in.smartjava.embedded;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ua.in.smartjava.base.BaseJpaTest;
import ua.in.smartjava.collection.EmployeeCollection;
import ua.in.smartjava.collection.VacationEntry;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmbeddedTest extends BaseJpaTest {

    @Test
    public void testFieldAcces() {
        entityManager.getTransaction().begin();
        CompanyEmbedded company = new CompanyEmbedded();
        entityManager.persist(company);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testCollection() {
        entityManager.getTransaction().begin();
        EmployeeCollection empl1 = getEmployeeCollection(5, "John");
        EmployeeCollection empl2 = getEmployeeCollection(6, "Jimmy");

        entityManager.persist(empl1);
        entityManager.persist(empl2);

        entityManager.getTransaction().commit();
    }

    private EmployeeCollection getEmployeeCollection(int namesCount, String name) {
        EmployeeCollection empl = new EmployeeCollection();
        empl.setName(name);

        empl.setNickNames(
                IntStream.range(0, namesCount).mapToObj(i -> "nick" + i).collect(Collectors.toSet())
        );
        empl.setVacationEntries(IntStream.range(0, 5).mapToObj(s -> new VacationEntry(new Date(), s)).collect(Collectors.toList()));
        return empl;
    }
}