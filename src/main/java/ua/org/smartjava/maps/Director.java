package ua.org.smartjava.maps;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAPS_DIRECTOR")
@Getter
@Setter
public class Director {
    @Id
    private long id;
    private String name;
    private long salary;

    @ElementCollection
    @CollectionTable(name = "MAPS_DIRECTOR_PHONES")
    @MapKeyColumn(name = "DIR_TYPE")
    @Column(name = "PHONE_NUM")
    private Map<String, String> phoneNumbers;
}
