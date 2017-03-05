package ua.org.smartjava.validate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import ua.org.smartjava.base.BaseJpaTest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SessionTest extends BaseJpaTest {

    @Autowired
    Validator validator;

    @Test
    public void testCreateManualValidator() {
        /* GIVEN */
        /* WHEN */
        Session awsSession = Session.builder()
                .attended(true)
                .name("AWS")
                .plannedDate(new Date())
                .build();
        /* THEN */
        validator.validate(awsSession);
    }

    @Test
    public void testCreateFailValidator() {
        /* GIVEN */
        /* WHEN */
        Session awsSession = Session.builder()
                .attended(false)
                .name("12345678912345")
                .plannedDate(new Date())
                .build();
        /* THEN */
        Set<ConstraintViolation<Session>> constraintViolations = validator.validate(awsSession);
        assertTrue(constraintViolations.size() == 4);
    }

    @Test
    public void testValidateGroup() {
        /* GIVEN */
        Session testSession = Session.builder()
                .attended(false)
                .name("12345678912345")
                .plannedDate(new Date())
                .group("TEST_GROUP")
                .build();
        /* WHEN */
        Set<ConstraintViolation<Session>> validated = validator.validate(testSession, GroupTime.class);
        Assert.assertTrue(validated.size() == 1);
        /* THEN */
    }

    @Test
    public void testAwsConstraintFail() {
        /* GIVEN */
        Session notAwsSession = Session.builder()
                .name("MySQL")
                .attended(true)
                .build();
        /* WHEN */
        Set<ConstraintViolation<Session>> validate = validator.validate(notAwsSession);
        /* THEN */
        Assert.assertTrue(validate.size() == 1);
    }

    @Test
    public void testAwsConstraintSuccess() {
        /* GIVEN */
        Session notAwsSession = Session.builder()
                .name("AWS")
                .attended(true)
                .build();
        /* WHEN */
        Set<ConstraintViolation<Session>> validate = validator.validate(notAwsSession);
        /* THEN */
        Assert.assertTrue(validate.size() == 0);
    }
}