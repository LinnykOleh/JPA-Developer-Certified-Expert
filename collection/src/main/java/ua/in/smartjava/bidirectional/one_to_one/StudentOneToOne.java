package ua.in.smartjava.bidirectional.one_to_one;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

/**
 * create table STUDENT_1_1_BI (
 *      id bigint not null auto_increment,
 *      name varchar(255),
 *      ticket_id integer,
 *      primary key (id))
 *
 * alter table STUDENT_1_1_BI add constraint FK7m3y862udoigdyyvi7yhng8gh foreign key (ticket_id) references TICKET_1_1_BI (id)
 */
@Entity
@Table(name = "STUDENT_1_1_BI")
@Builder
@Data
public class StudentOneToOne {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    private TicketOneToOne ticket;

}
