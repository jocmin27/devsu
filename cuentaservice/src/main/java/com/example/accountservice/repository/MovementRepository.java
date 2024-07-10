package com.example.accountservice.repository;

import com.example.accountservice.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate startDate, LocalDate endDate);
}

