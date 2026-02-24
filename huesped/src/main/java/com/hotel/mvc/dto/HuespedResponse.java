package com.hotel.mvc.dto;

public record HuespedResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String tipoDocumento,
        String nacionalidad,
        String estadoRegistro
) {}