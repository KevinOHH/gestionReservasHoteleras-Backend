package com.hotel.mvc.controllers;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import com.hotel.mvc.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends CommonController<UsuarioRequest, UsuarioResponse, UsuarioService> {

    public UsuarioController(UsuarioService service) {
        super(service);
    }
}