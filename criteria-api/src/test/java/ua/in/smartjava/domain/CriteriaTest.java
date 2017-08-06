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
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CriteriaTest {

    private static final String NEW_YORK = "New York";

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private long insertedId;

    @Before
    public void init() {
        this.entityManager = this.entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("DELETE FROM Address");
        query.executeUpdate();
        entityManager.getTransaction().commit();

        //Prepare data
        entityManager.getTransaction().begin();
        Address addedAddress = Address.builder().street(NEW_YORK).district("DIS").build();
        entityManager.persist(addedAddress);
        entityManager.getTransaction().commit();
        insertedId = addedAddress.getId();
    }

    @Test
    public void testCriteria() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("street"), NEW_YORK));

        // When
        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        // Then
        List<Address> resultList = query.getResultList();
        assertEquals(1, resultList.size());
        assertEquals(NEW_YORK, resultList.get(0).getStreet());
    }

    @Test
    public void testCriteriaUntyped() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("street"), NEW_YORK));

        // When
        Query query = entityManager.createQuery(criteriaQuery);

        // Then
        List<Address> resultList = query.getResultList();
        assertEquals(1, resultList.size());
        assertEquals(NEW_YORK, resultList.get(0).getStreet());
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
        typedQuery.setParameter("name", NEW_YORK);
        List<Address> resultList = typedQuery.getResultList();

        // Then
        assertEquals(1, resultList.size());
        assertEquals(NEW_YORK, resultList.get(0).getStreet());
    }

    @Test
    public void testCartesian() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root);

        // When
        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

        // Then
        assertEquals(1, query.getResultList().size());
    }

    /**
     * Hibernate: select address0_.street as col_0_0_ from address address0_
     */
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
        assertEquals(NEW_YORK, result);
    }

    @Test
    public void testMultipleExpressions() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("street"), NEW_YORK));
        criteriaQuery.select(criteriaBuilder.tuple(root.get("street"), root.get("district")));

        // When
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = query.getResultList();

        // Then
        assertEquals(1, resultList.size());
        Tuple tuple = resultList.get(0);
        assertEquals(NEW_YORK, tuple.get(0));
        assertEquals("DIS", tuple.get(1));
    }

    /**
     * Hibernate: select address0_.street as col_0_0_, address0_.street as col_1_0_, address0_.district as col_2_0_
     * from address address0_ where address0_.street=?
     */
    @Test
    public void testMultiselect() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("street"), NEW_YORK));
        criteriaQuery.multiselect(root.get("street"), root.get("street"), root.get("district"));

        // When
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        Tuple tuple = query.getSingleResult();

        // Then
        assertEquals(NEW_YORK, tuple.get(0));
        assertEquals(NEW_YORK, tuple.get(1));
        assertEquals("DIS", tuple.get(2));
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
        assertEquals(2, singleResult.length);
        assertEquals(NEW_YORK, singleResult[0]);
        assertEquals("DIS", singleResult[1]);
    }

    /**
     * Hibernate: select address0_.street as col_0_0_, address0_.district as col_1_0_ from address address0_
     */
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
        AddressInfo result = query.getSingleResult();
        assertEquals("DIS", result.getRegion());
        assertEquals(NEW_YORK, result.getName());
    }

    @Getter
    @AllArgsConstructor
    public static class AddressInfo {
        private String name;
        private String region;

    }

    /**
     * Hibernate: select address0_.id as col_0_0_, address0_.street as col_1_0_, address0_.district as col_2_0_ from
     * address address0_
     */
    @Test
    public void testAliases() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
        Root<Address> root = tupleQuery.from(Address.class);
        CriteriaQuery<Tuple> criteriaQuery = tupleQuery.multiselect(
                root.get("id").alias("first"),
                root.get("street").alias("second"),
                root.get("district").alias("third"));

        // When
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        Tuple singleResult = query.getSingleResult();

        // Then
        assertEquals(3, singleResult.toArray().length);
        assertEquals(this.insertedId, singleResult.get("first"));
        assertEquals(NEW_YORK, singleResult.get("second"));
        assertEquals("DIS", singleResult.get("third"));
    }

    /**
     * Hibernate: insert into address (id, district, street) values (null, ?, ?)
     */
    @Test
    public void testIn() {
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(
                        root.get("street").in(NEW_YORK, "Old")
//                        This is the same
//                        criteriaBuilder.in(root.get("street")).value(NEW_YORK).value("Old")
                );

        // When
        TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);
        Address singleResult = query.getSingleResult();

        // Then
        assertEquals(NEW_YORK, singleResult.getStreet());
    }

    /**
     * Hibernate: delete from address where street is not null
     */
    @Test
    public void testCriteriaDelete() {
        entityManager.getTransaction().begin();
        // Given
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Address> criteriaDelete = criteriaBuilder.createCriteriaDelete(Address.class);
        Root<Address> root = criteriaDelete.from(Address.class);
        criteriaDelete.where(criteriaBuilder.isNotNull(root.get("street")));

        // When
        Query query = entityManager.createQuery(criteriaDelete);
        int result = query.executeUpdate();

        entityManager.getTransaction().commit();

        // Then
        assertEquals(1, result);
    }

}