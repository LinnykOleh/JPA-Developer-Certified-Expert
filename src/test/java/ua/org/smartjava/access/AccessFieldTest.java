package ua.org.smartjava.access;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ua.org.smartjava.BaseJpaTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessFieldTest extends BaseJpaTest {

    @Test
    public void testFieldAcces() {
        entityManager.getTransaction().begin();
        AccessField accessField = new AccessField();
        accessField.name = "mega";
        entityManager.persist(accessField);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testNoGetters() {
        entityManager.getTransaction().begin();
        PropertyAccess noaccess = new PropertyAccess();
        entityManager.persist(noaccess);
        entityManager.getTransaction().commit();
    }

    @Test
    public void test() {
        entityManager.getTransaction().begin();
        PropertyAccess propertyAccess = new PropertyAccess();
        propertyAccess.setPhoneFromDb("1234567890");
        entityManager.persist(propertyAccess);
        entityManager.getTransaction().commit();
        Assert.assertEquals("+3801234567890", propertyAccess.getPhoneFromDb());
    }
}