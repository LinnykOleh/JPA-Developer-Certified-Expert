package ua.in.smartajva.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Manufa {

    @EmbeddedId
    private ManufactorEmbeddableId id;

    private String name;
}
