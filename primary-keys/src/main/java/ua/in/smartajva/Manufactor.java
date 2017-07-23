package ua.in.smartajva;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
@IdClass(ManufactorId.class)
public class Manufactor {
    @Id
    private String country;
    @Id
    @Column(name = "MANUF_ID")
    private int id;

    private String name;

    public Manufactor(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufactor() {}
}
