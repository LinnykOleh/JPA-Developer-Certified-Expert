package ua.in.smartjava.maps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import ua.in.smartjava.base.BaseJpaTest;
import ua.in.smartjava.maps.base.MapBase;
import ua.in.smartjava.maps.base.PhoneType;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapsTest extends BaseJpaTest {

    @Test
    public void testMapKeyBaseTypes() {
        entityManager.getTransaction().begin();
        HashMap<String, String> phones = new HashMap<>();
        phones.put("work", "1234");
        phones.put("home", "777");
        MapBase mapBase = MapBase.builder().name("NAME").build();
        mapBase.setPhoneNumbers(phones);
        entityManager.persist(mapBase);
        entityManager.getTransaction().commit();

        MapBase mapBase1 = entityManager.find(MapBase.class, mapBase.getId());
        assertEquals("NAME", mapBase1.getName());
        Map<String, String> phoneNumbers = mapBase1.getPhoneNumbers();
        assertEquals("1234", phoneNumbers.get("work"));
        assertEquals("777", phoneNumbers.get("home"));
    }

    @Test
    public void testMapKeyEnums() {
        entityManager.getTransaction().begin();
        HashMap<PhoneType, String> phones = new HashMap<>();
        phones.put(PhoneType.WORK, "1234");
        phones.put(PhoneType.HOME, "777");
        MapBase mapBase = MapBase.builder().name("NAME").build();
        mapBase.setPhones(phones);
        entityManager.persist(mapBase);
        entityManager.getTransaction().commit();
        Map<String, Object> properties = entityManager.getProperties();
        properties.forEach((k, v) -> {
            System.err.println(k + ":" + v);
        });
    }

}