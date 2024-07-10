package com.example.clientservice;

import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTests {

    @InjectMocks
    private ClientService clienteService;

    @Mock
    private ClientRepository clienteRepository;

    private Client cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Client();
        cliente.setId(1L);
        cliente.setNombre("John Doe");
    }

    @Test
    void testCreateCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Client createdCliente = clienteService.saveCliente(cliente);
        assertNotNull(createdCliente);
    }
}

