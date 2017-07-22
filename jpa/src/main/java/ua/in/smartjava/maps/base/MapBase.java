package ua.in.smartjava.maps.base;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAPS_BASE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "MAPS_BASE_PHONES")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    private Map<String, String> phoneNumbers;

    @ElementCollection
    @CollectionTable(name = "MAPS_ENUM_PHONES")
    @MapKeyEnumerated(value = EnumType.STRING)
    @MapKeyColumn(name = "PHONE_TYPE")
    private Map<PhoneType, String> phones;
}

