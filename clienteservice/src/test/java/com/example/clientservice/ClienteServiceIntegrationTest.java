package com.example.clientservice;

import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.service.ClientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClientService clienteService;

    @Autowired
    private ClientRepository clienteRepository;

    private Client cliente;

    @BeforeEach
    void setUp() {
        cliente = new Client();
        cliente.setNombre("John Doe");
    }

    @Test
    void testCreateAndGetCliente() {
        Client createdCliente = clienteService.saveCliente(cliente);
        assertNotNull(createdCliente);
        assertNotNull(createdCliente.getId());

        Client foundCliente = clienteService.getClienteById(createdCliente.getId());
        assertNotNull(foundCliente);
        assertEquals("John Doe", foundCliente.getNombre());
    }
}
