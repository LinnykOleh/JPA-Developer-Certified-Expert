package ua.in.smartjava.elementCollection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Activity {

    @Id
    @GeneratedValue
    private int id;
}
