package com.example.clientservice.service;

import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client saveCliente(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClientes() {
        return clientRepository.findAll();
    }

    public Client getClienteById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteCliente(Long id) {
        clientRepository.deleteById(id);
    }
}
