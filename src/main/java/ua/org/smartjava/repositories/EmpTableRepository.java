package ua.org.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.org.smartjava.domain.primaryKey.EmpAuto;
import ua.org.smartjava.domain.primaryKey.EmpTable;

public interface EmpTableRepository extends JpaRepository<EmpTable, Long> {
}
