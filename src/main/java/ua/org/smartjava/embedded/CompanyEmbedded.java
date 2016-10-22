package ua.org.smartjava.embedded;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompanyEmbedded {
    @Id
    private long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "TOWN")),
            @AttributeOverride(name = "country", column = @Column(name = "PROVANCE"))
    }
    )
    private Address address;
}