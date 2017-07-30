package ua.in.smartjava.bidirectional.one_to_one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

/**
 * create table TICKET_1_1_BI (
 *      id integer not null auto_increment,
 *      num integer not null,
 *      primary key (id))
 */
@Entity
@Table(name = "TICKET_1_1_BI")
@Builder
@Data
public class TicketOneToOne {

    @Id
    @GeneratedValue
    private int id;

    private int num;

    /**
     * If you forgot to add mappedBy - then additional column will be created in TicketOneToOne table
     * We will have 2 unidirectional mappings - not bidirectional
     *  create table TICKET_1_1_BI (
     *      id integer not null auto_increment,
     *      num integer not null,
     *      student_id bigint,
     *      primary key (id))
     */
    @OneToOne (mappedBy = "ticket")
    private StudentOneToOne student;
}
