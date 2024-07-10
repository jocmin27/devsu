package com.example.accountservice.service;

import com.example.accountservice.entity.Account;
import com.example.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account saveCuenta(Account cuenta) {
        return accountRepository.save(cuenta);
    }

    public List<Account> getAllCuentas() {
        return accountRepository.findAll();
    }

    public Account getCuentaById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteCuenta(Long id) {
        accountRepository.deleteById(id);
    }
}
