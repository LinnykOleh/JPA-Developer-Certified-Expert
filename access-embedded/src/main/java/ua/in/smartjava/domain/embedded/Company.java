package ua.in.smartjava.domain.embedded;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;

@Entity
@Builder
public class Company {
    @Id
    private long id;

    private String name;

    @Embedded
    @AttributeOverrides({
                    @AttributeOverride(name = "city", column = @Column(name = "CMP_CITY")),
                    @AttributeOverride(name = "country", column = @Column(name = "CMP_COUNTRY"))
            })
    private Address address;
}