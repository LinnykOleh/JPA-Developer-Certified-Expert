package ua.org.smartjava.embedded;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ua.org.smartjava.BaseJpaTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmbeddedTest extends BaseJpaTest{
    @Test
    public void testFieldAcces() {
        entityManager.getTransaction().begin();
        CompanyEmbedded company = new CompanyEmbedded();
        entityManager.persist(company);
        entityManager.getTransaction().commit();
    }
}