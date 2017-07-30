package ua.in.smartjava.unidirectional.one_to_many;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create table Wheel (id integer not null auto_increment, primary key (id))
 * 
 * create table CAR_WHEEL (CAR_ID integer not null, EH_ID integer not null)
 * alter table CAR_WHEEL add constraint UK_d12rnajy97rwrj3krngdl05k5 unique (EH_ID)
 * alter table CAR_WHEEL add constraint FK92op0i9w61nm9jb2e9krargv9 foreign key (EH_ID) references Wheel (id)
 * alter table CAR_WHEEL add constraint FKrk0en6supnix5llm4n8my8tmt foreign key (CAR_ID) references Car (id)
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wheel {

    @Id
    @GeneratedValue
    private int id;
}
