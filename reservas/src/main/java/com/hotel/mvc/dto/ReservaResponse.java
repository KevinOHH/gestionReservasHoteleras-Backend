package com.hotel.mvc.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.mvc.dto.DatosHabitacion;
import com.hotel.mvc.dto.DatosHuesped;
import com.hotel.mvc.enums.EstadoReserva;


public record ReservaResponse(
		
		Long id,
		DatosHuesped huesped,
		DatosHabitacion habitacion,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fechaEntrada,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fechaSalida,
		String estadoReserva
		
) {}
