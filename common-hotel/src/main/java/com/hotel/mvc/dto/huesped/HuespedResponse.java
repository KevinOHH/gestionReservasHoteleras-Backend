package com.hotel.mvc.dto.huesped;

public record HuespedResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String tipoDocumento,
        String nacionalidad,
        String estadoRegistro //para fines de pruebas
) {}