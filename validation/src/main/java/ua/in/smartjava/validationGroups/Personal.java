package ua.in.smartjava.validationGroups;

import javax.validation.groups.Default;

/**
 * This means that when we are validating with Personal group
 * also all constrains of Default group are validated too.
 */
public interface Personal extends Default{
}
