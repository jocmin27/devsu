package com.example.clientservice.controller;

import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.repository.PersonRepository;
import com.example.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clienteService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonRepository personaRepository;

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        // Save the Cliente object which also saves the Persona part
        return clientRepository.save(client);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateClientPartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Client.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, client, value);
            }
        });

        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }


    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
