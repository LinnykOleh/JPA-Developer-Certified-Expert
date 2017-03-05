package ua.org.smartjava.maps;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;

import ua.org.smartjava.base.BaseJpaTest;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EntityScan(
//        basePackages = "ua.org.smartjava.maps"
//)
public class MapsTest extends BaseJpaTest {
//    @Autowired
//    private DirectorRepository directorRepository;

    @After
    public void clear() {
//        directorRepository.deleteAll();
    }

    @Test
    public void testMapStringString() {
        entityManager.getTransaction().begin();
        HashMap<String, String> phones = new HashMap<>();
        phones.put("work", "1234");
        phones.put("home", "777");
        Director director = new Director();
        director.setPhoneNumbers(phones);
        entityManager.persist(director);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testMapEnumKey() {
        entityManager.getTransaction().begin();
        HashMap<PhoneType, String> phones = new HashMap<>();
        phones.put(PhoneType.WORK, "1234");
        phones.put(PhoneType.HOME, "777");
        phones.put(PhoneType.MOBILE, "097");
        Deputat director = new Deputat();
        director.setPhoneNumbers(phones);
        entityManager.persist(director);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testMapTemporalKey() {
        entityManager.getTransaction().begin();

        HashMap<Date, String> events = new HashMap<>();
        events.put(new Date(), "1234");
        events.put(new Date(213123123), "777");
        events.put(new Date(12), "097");
        Calendar director = new Calendar();
        director.setEvents(events);
        entityManager.persist(director);
        entityManager.getTransaction().commit();
    }

}