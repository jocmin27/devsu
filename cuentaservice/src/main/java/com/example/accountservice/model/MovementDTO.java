package com.example.accountservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovementDTO {
    private LocalDate fecha;
    private String tipoMovimiento;
    private double valor;
    private Long cuentaId;

    // Getters y Setters
}
