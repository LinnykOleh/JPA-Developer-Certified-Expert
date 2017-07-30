package ua.in.smartjava.domain.lazy;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LazyTable")
@NoArgsConstructor
@AllArgsConstructor
public class LazyClass {
    @Id
    @Getter
    private long id;

    @Getter @Setter
    private String name;

//  This does not work with hibernate implementation.
    @Basic(fetch = FetchType.LAZY)
    @Getter @Setter
    private String position;
}
