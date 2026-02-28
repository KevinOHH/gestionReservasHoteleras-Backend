package com.hotel.mvc.controllers;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import com.hotel.mvc.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/usuario")
public class UsuarioController extends CommonController<UsuarioRequest, UsuarioResponse, UsuarioService> {

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(@Validated @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable @Positive(message = "El Id debe ser positivo") Long id,
            @Validated @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }
}