package ua.org.smartjava.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class VacationEntry {

    @Temporal(value = TemporalType.DATE)
    @Getter @Setter
    private Date startDate;

    @Column(name = "DAYS")
    @Getter @Setter
    private int daysTaken;

    public VacationEntry(Date startDate, int days) {
        this.startDate = startDate;
        this.daysTaken = days;
    }
}