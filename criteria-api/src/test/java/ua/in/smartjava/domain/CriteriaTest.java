package ua.in.smartjava.domain;

import org.junit.After;
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
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();

        //Prepare data
        entityManager.getTransaction().begin();
        entityManager.persist(Address.builder().street("New").district("DIS").build());
        entityManager.getTransaction().commit();
    }

    @After
    public void tearDown() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("DELETE FROM Address");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void testCriteria() {
        // Given
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

    @Test
    public void testDynamicCriteria() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .distinct(true);

        ParameterExpression<String> streetName = criteriaBuilder.parameter(String.class, "name");
        criteriaQuery.where(criteriaBuilder.equal(root.get("street"), streetName));

        // When
        TypedQuery<Address> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("name", "New");
        List<Address> resultList = typedQuery.getResultList();

        // Then
        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void testCartesian() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> from = criteriaQuery.from(Address.class);
        criteriaQuery.from(Address.class);
        criteriaQuery.select(from);

        // When
        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        // Then
        Assert.assertEquals(1, query.getResultList().size());
    }

    @Test
    public void testSingleExpression() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root.get("street"));

        // When
        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        String result = query.getSingleResult();

        // Then
        Assert.assertEquals("New", result);
    }

    @Test
    public void testMultipleExpressions() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("street"), "New"));
        criteriaQuery.select(criteriaBuilder.tuple(root.get("street"), root.get("district")));

        // When
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = query.getResultList();

        // Then
        Assert.assertEquals(1, resultList.size());
        Tuple tuple = resultList.get(0);
        Assert.assertEquals("New", tuple.get(0));
        Assert.assertEquals("DIS", tuple.get(1));
    }

    @Test
    public void testMultiselect() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("street"), "New"));
        criteriaQuery.multiselect(root.get("street"), root.get("street"), root.get("district"));

        // When
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        Tuple tuple = query.getSingleResult();

        // Then
        Assert.assertEquals("New", tuple.get(0));
        Assert.assertEquals("New", tuple.get(1));
        Assert.assertEquals("DIS", tuple.get(2));
    }

    @Test
    public void testArray() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.multiselect(root.get("street"), root.get("district"));

        // When
        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        Object[] singleResult = query.getSingleResult();

        // Then
        Assert.assertEquals(2, singleResult.length);
    }

    @Test
    public void testConstruct() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressInfo> criteriaQuery = criteriaBuilder.createQuery(AddressInfo.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(criteriaBuilder.construct(AddressInfo.class, root.get("street"), root.get("district")));

        // When
        TypedQuery<AddressInfo> query = entityManager.createQuery(criteriaQuery);

        // Then
        List<AddressInfo> resultList = query.getResultList();
        Assert.assertEquals(1, resultList.size());
        AddressInfo addressInfo = resultList.get(0);
        Assert.assertEquals("DIS",addressInfo.getRegion());
        Assert.assertEquals("New",addressInfo.getName());
    }

    @Getter
    @AllArgsConstructor
    public static class AddressInfo {
        private String name;
        private String region;

    }

}