package com.hotel.mvc.dto;

import java.math.BigDecimal;

import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.TipoHabitacion;

public record HabitacionResponse(
		Long id,
        Integer numero,                 
        TipoHabitacion tipo,
        BigDecimal precio,
        Integer capacidad,
        EstadoHabitacion estadoHabitacion
) {}