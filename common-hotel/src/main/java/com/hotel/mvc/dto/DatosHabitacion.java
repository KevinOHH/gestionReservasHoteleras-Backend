package com.hotel.mvc.dto;

import java.math.BigDecimal;

public record DatosHabitacion(
	
		String numero,
		String tipo,
		BigDecimal precio,
		Integer capacidad
		
) {}
