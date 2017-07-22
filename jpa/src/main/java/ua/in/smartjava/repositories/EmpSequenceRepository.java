package ua.in.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.in.smartjava.domain.primaryKey.EmpSequence;

public interface EmpSequenceRepository extends JpaRepository<EmpSequence, Long> {
}
