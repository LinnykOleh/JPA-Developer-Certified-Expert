package ua.in.smartjava.unidirectional.one_to_one;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;

/**
 * create table StudentOneToOne (
 *      id bigint not null auto_increment,
 *      name varchar(255),
 *      ticket_id integer,
 *      primary key (id))
 *
 * alter table StudentOneToOne add constraint FKq9qj71aevj55yky35m3tnch8g foreign key (ticket_id) references TicketOneToOne (id)
 */
@Entity
@Builder
@Data
public class Student {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Ticket ticket;

}
