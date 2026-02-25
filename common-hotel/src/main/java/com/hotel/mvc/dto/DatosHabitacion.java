package com.hotel.mvc.dto;

import java.math.BigDecimal;

import com.hotel.mvc.enums.TipoHabitacion;

public record DatosHabitacion(
	
		Integer numero,
		//String tipo,
		TipoHabitacion tipo,
		BigDecimal precio,
		Integer capacidad
		
) {}
