package com.hotel.mvc.dto;

import java.util.Set;

public record UsuarioResponse(
        Long id,
        String username,
        Set<String> roles,
        String estadoRegistro
) {}