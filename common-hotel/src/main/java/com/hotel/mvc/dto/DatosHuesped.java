package com.hotel.mvc.dto;

public record DatosHuesped(
		
		Long id,
		String nombre,
		String email,
		String telefono, 
		String tipoDocumento,
		String nacionalidad
		
) {}
