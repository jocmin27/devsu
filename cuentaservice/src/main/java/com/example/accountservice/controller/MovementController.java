package com.example.accountservice.controller;

import com.example.accountservice.entity.Movement;
import com.example.accountservice.model.MovementDTO;
import com.example.accountservice.service.MovementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movimientos")
@Log4j2
public class MovementController {
    @Autowired
    private MovementService movementService;

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody MovementDTO movementDTO) {


            Movement movement = movementService.crearMovimiento(movementDTO);
            return new ResponseEntity<>(movement, HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Movement> updateMovementPartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        Movement movement = movementService.getMovimientoById(id);
        if (movement == null) {
            return ResponseEntity.notFound().build();
        }

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Movement.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, movement, value);
            }
        });

        movementService.saveMovimiento(movement);
        return ResponseEntity.ok(movement);
    }

    @GetMapping
    public ResponseEntity<List<Movement>> getAllMovimientos() {
        return ResponseEntity.ok(movementService.getAllMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovimientoById(@PathVariable Long id) {
        return ResponseEntity.ok(movementService.getMovimientoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        movementService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}

