package ua.in.smartjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.in.smartjava.domain.primaryKey.EmpAuto;

public interface EmpAutoRepository extends JpaRepository<EmpAuto, Long> {
}
