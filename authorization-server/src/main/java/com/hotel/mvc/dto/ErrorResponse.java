package com.hotel.mvc.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) { }

