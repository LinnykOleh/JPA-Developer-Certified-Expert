package ua.in.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.in.smartjava.domain.primaryKey.EmpTable;

public interface EmpTableRepository extends JpaRepository<EmpTable, Long> {
}
