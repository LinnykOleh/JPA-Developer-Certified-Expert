package ua.org.smartjava.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Indicate that the session is about AWS topic.
 * May be applied on fields or properties of type Session.
 */
@Constraint(validatedBy = SessionIsAwsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aws {

    String message() default "Session must be about AWS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean includeAws() default true;

}