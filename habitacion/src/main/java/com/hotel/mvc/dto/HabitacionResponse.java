package com.hotel.mvc.dto;

import java.math.BigDecimal;

import com.hotel.mvc.enums.EstadoHabitacion;

public record HabitacionResponse(
        Long id,
        String numero,
        String tipo,
        BigDecimal precio,
        Integer capacidad,
        EstadoHabitacion estadoHabitacion,
        String estado
) {}