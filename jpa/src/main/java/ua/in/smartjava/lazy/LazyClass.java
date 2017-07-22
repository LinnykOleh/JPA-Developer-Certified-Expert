package ua.in.smartjava.lazy;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Lazy")
public class LazyClass {
    @Id
    private long id;

    @Getter @Setter
    private String name;

//  This does not work with hibernate implementation.
//    TODO check why ?
    @Basic(fetch = FetchType.LAZY)
    @Getter @Setter
    private String position;
}
