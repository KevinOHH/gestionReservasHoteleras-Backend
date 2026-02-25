package com.hotel.mvc.dto.habitacion;

import java.math.BigDecimal;

import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.TipoHabitacion;

public record HabitacionResponse(
        Long id,
        Integer numero,
        //String tipo,
        TipoHabitacion tipo,
        BigDecimal precio,
        Integer capacidad,
        EstadoHabitacion estadoHabitacion,
        String estado
) {}