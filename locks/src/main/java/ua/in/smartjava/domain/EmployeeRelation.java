package ua.in.smartjava.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class EmployeeRelation {
    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private String street;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;

    @PostLoad
    public void load() {
        log.info("PostLoad");
    }
}