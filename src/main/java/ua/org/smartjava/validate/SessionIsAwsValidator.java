package ua.org.smartjava.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SessionIsAwsValidator implements ConstraintValidator<Aws, Session> {
    private static final String AWS_PATTERN = "AWS";
    private boolean includeAws;

    @Override
    public void initialize(Aws session) {
        includeAws = session.includeAws();
    }

    @Override
    public boolean isValid(Session session, ConstraintValidatorContext constraintValidatorContext) {
        return session.getName().contains(AWS_PATTERN);
    }
}