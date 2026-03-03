package com.hotel.mvc.controllers;

import com.hotel.mvc.dto.UsuariRequest;
import com.hotel.mvc.dto.UsuariResponse;
import com.hotel.mvc.service.UsuarioServic;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuariController extends CommonController<UsuariRequest, UsuariResponse, UsuarioServic> {

    public UsuariController(UsuarioServic service) {
        super(service);
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuariResponse> registrar(@Valid @RequestBody UsuariRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UsuariResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuariRequest request) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }
}