package ua.in.smartjava.unidirectional.one_to_one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

/**
 * create table TicketOneToOne (
 *      id integer not null auto_increment,
 *      num integer not null,
 *      primary key (id))
 */
@Entity
@Builder
@Data
public class Ticket {

    @Id
    @GeneratedValue
    private int id;

    private int num;
}
