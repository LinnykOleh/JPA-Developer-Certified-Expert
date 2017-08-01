package ua.in.smartajva.serialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * create table En1 (
 *      id integer not null auto_increment,
 *      address tinyblob,
 *      primary key (id))
 */
@Entity
public class En1 {

    @Id
    @GeneratedValue
    private int id;

//    it must implement Serializable
    private AddressSerializable address;
}
