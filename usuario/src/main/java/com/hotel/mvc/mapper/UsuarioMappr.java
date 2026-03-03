package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.UsuariRequest;
import com.hotel.mvc.dto.UsuariResponse;
import com.hotel.mvc.entities.Usuariop;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMappr {

    public Usuariop toEntity(UsuariRequest request) {
        return Usuariop.builder()
                .username(request.username())
                .rol(request.rol())
                .build();
    }

    public UsuariResponse toResponse(Usuariop usuario) {
        return new UsuariResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }

    public void updateEntity(UsuariRequest request, Usuariop usuario) {
        usuario.setUsername(request.username());
        usuario.setRol(request.rol());
    }
}