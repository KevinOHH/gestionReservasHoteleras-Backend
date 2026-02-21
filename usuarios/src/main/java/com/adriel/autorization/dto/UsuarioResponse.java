package com.adriel.autorization.dto;

import com.adriel.autorization.enums.EstadoRegistro;
import com.adriel.autorization.enums.Rol;

//Response
public record UsuarioResponse(
 Long id,
 String username,
 Rol rol,
 EstadoRegistro estado
) {}
