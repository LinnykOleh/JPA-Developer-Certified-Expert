package ua.in.smartjava.unidirectional.many_to_many;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Man {

    @Id
    @GeneratedValue
    private int id;

    private String color;

    @ManyToMany
    @JoinTable(
            name = "MAN_ROOM",
            joinColumns = @JoinColumn(name = "MAN_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_ID")
    )
    private Collection<Room> rooms;
}
