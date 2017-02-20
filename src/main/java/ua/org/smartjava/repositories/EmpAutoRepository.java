package ua.org.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.org.smartjava.domain.primaryKey.EmpAuto;

public interface EmpAutoRepository extends JpaRepository<EmpAuto, Long> {
}
