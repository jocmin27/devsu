package com.example.accountservice.controller;

import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Movement;
import com.example.accountservice.repository.MovementRepository;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MovementRepository movementRepository;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account cuenta) {
        return ResponseEntity.ok(accountService.saveCuenta(cuenta));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Account> updateAccountPartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Account account = accountService.getCuentaById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Account.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, account, value);
            }
        });

        accountService.saveCuenta(account);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(accountService.getAllCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getCuentaById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getCuentaById(id));
    }

    @GetMapping("/movimientos")
    public List<Movement> getMovementsByAccountIdAndDate(
            @RequestParam Long cuentaId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return movementRepository.findByCuentaIdAndFechaBetween(cuentaId, startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

