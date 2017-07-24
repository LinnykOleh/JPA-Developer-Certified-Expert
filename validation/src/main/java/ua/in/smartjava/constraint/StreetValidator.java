package ua.in.smartjava.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StreetValidator implements ConstraintValidator<Street, String>{
    private String street;

    @Override
    public void initialize(Street constraintAnnotation) {
        this.street = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return street.equals(value);
    }
}
