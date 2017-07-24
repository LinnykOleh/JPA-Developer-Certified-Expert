package ua.in.smartajva.singleTable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CompanyEmployee extends Employee {
    private int vacation;
}
