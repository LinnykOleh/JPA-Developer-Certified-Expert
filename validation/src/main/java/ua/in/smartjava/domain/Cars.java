package ua.in.smartjava.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cars {
    @Id
    @GeneratedValue
    private long id;

    private String name;
}
