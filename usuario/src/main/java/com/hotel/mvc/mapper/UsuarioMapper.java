package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import com.hotel.mvc.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .username(request.username())
                .rol(request.rol())
                .build();
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }

    public void updateEntity(UsuarioRequest request, Usuario usuario) {
        usuario.setUsername(request.username());
        usuario.setRol(request.rol());
    }
}