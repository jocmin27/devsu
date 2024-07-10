package com.example.accountservice.service;

import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Movement;
import com.example.accountservice.exception.CustomException;
import com.example.accountservice.exception.InsufficientBalanceException;
import com.example.accountservice.model.MovementDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Movement saveMovimiento(Movement movement) throws Exception {
        Account cuenta = accountRepository.findById(movement.getCuenta().getId()).orElse(null);
        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada");
        }

        double nuevoSaldo = cuenta.getSaldoInicial() + movement.getValor();
        if (nuevoSaldo < 0) {
            throw new Exception("Saldo no disponible");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        accountRepository.save(cuenta);
        movement.setSaldo(nuevoSaldo);
        return movementRepository.save(movement);
    }

    public Movement crearMovimiento(MovementDTO movementDTO) {
        Account cuenta = accountRepository.findById(movementDTO.getCuentaId())
                .orElseThrow(() -> new CustomException("Cuenta no encontrada"));

        double nuevoSaldo = cuenta.getSaldoInicial() + movementDTO.getValor();
        if (nuevoSaldo < 0) {
            throw new InsufficientBalanceException();
        }

        Movement movement = new Movement();
        movement.setFecha(movementDTO.getFecha());
        movement.setTipoMovimiento(movementDTO.getTipoMovimiento());
        movement.setValor(movementDTO.getValor());
        movement.setCuenta(cuenta);
        movement.setSaldo(cuenta.getSaldoInicial() + movementDTO.getValor());

        cuenta.getMovements().add(movement);

        return movementRepository.save(movement);
    }

    public List<Movement> getAllMovimientos() {
        return movementRepository.findAll();
    }

    public Movement getMovimientoById(Long id) {
        return movementRepository.findById(id).orElse(null);
    }

    public void deleteMovimiento(Long id) {
        movementRepository.deleteById(id);
    }
}

