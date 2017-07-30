package ua.in.smartjava.basic;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;

import lombok.Builder;
import lombok.Data;

/**
 * create table EmployeeEnum (
 *      id integer not null auto_increment,
 *      name varchar(255),
 *      primary key (id))
 */
@Entity
@Builder
@Data
public class EmployeeEnum {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    /**
     * create table EMP_ENUM_PHONES (
     *      EmployeeEnum_id integer not null,
     *      PHONE_NUM varchar(255),
     *      PHONE_TYPE varchar(255) not null,
     *      primary key (EmployeeEnum_id, PHONE_TYPE))
     *
     * alter table EMP_ENUM_PHONES add constraint FKmjb9rp6sksk8kacfbts5eu2m4 foreign key (EmployeeEnum_id) references EmployeeEnum (id)
     *
     */
    @ElementCollection
    @CollectionTable(name = "EMP_ENUM_PHONES")
    @MapKeyEnumerated(value = EnumType.STRING)
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUM")
    Map<PhoneType, String> phoneNumbers;
}