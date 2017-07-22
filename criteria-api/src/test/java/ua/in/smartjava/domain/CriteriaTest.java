package ua.in.smartjava.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Test
    public void getContextIsWorking() throws Exception {
        entityManager.getTransaction().begin();

        Address bestStreet = Address.builder().street("BestStreet").build();
        entityManager.persist(bestStreet);

        entityManager.getTransaction().commit();

        Assert.assertEquals("BestStreet", bestStreet.getStreet());
    }

    @Test
    public void testCriteria() {
        // Given
        entityManager.getTransaction().begin();
        entityManager.persist(Address.builder().street("New").build());
        entityManager.getTransaction().commit();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("street"), "New"));

        // When
        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        // Then
        List<Address> resultList = query.getResultList();
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals("New", resultList.get(0).getStreet());
    }

}