package com.hotel.mvc.dto;

import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.Rol;

public record UsuarioResponse(
		 Long id,
		 String username,
		 Rol rol,
		 EstadoRegistro estado
		) {}
