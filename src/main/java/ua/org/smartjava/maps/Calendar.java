package ua.org.smartjava.maps;

import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyTemporal;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAP_CAL")
@Getter
@Setter
public class Calendar {

    @Id
    private int id;

    @ElementCollection
    @CollectionTable(name = "MAP_CAL_EVENTS")
    @MapKeyTemporal(TemporalType.DATE)
    @MapKeyColumn(name = "EVENT_DATE")
    @Column(name = "EVENT_DESC")
    Map<Date, String> events;
}
