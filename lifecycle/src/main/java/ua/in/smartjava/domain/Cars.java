package ua.in.smartjava.domain;

import javax.persistence.Entity;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cars extends BaseLifecycleClass {
    private String name;
}
