package ua.org.smartjava.maps;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAP_DEP")
@Getter
@Setter
public class Deputat {

    @Id
    private int id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "MAP_DEP_PHONE")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "MAP_DIR_PHONE_TYPE")
    private Map<PhoneType, String> phoneNumbers;
}
enum PhoneType {
    HOME, WORK, MOBILE
}
