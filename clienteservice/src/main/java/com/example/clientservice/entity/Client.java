package com.example.clientservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Client extends Person {

    private String contrasena;
    private String estado;
    // Getters and Setters
}

