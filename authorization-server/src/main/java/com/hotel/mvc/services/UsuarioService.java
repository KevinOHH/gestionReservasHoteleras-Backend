package com.hotel.mvc.services;

import java.util.Set;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

