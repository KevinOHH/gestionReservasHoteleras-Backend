package com.hotel.mvc.services;

import java.util.Set;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {

	List<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(Long id);
    
    UsuarioResponse obtenerPorId(Long id);
    
    UsuarioResponse actualizar(Long id, UsuarioRequest request);
}

