package com.example.accountservice.service;

import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Movement;
import com.example.accountservice.model.ClienteDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.uri.clientes}")
    private String apiUrl;

    public List<Account> getEstadoCuenta(Long clienteId, LocalDate startDate, LocalDate endDate) {

        ClienteDTO cliente = restTemplate.getForObject(apiUrl + clienteId, ClienteDTO.class);

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // Obtener cuentas del cliente
        List<Account> cuentas = accountRepository.findByClienteId(clienteId);

        // Para cada cuenta, obtener movimientos en el rango de fechas
        cuentas.forEach(cuenta -> {
            List<Movement> movements = movementRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), startDate, endDate);
            cuenta.setMovements(movements);
        });

        return cuentas;
    }
}
