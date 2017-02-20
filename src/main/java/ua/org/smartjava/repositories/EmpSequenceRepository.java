package ua.org.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.org.smartjava.domain.primaryKey.EmpSequence;
import ua.org.smartjava.domain.primaryKey.EmpTable;

public interface EmpSequenceRepository extends JpaRepository<EmpSequence, Long> {
}
