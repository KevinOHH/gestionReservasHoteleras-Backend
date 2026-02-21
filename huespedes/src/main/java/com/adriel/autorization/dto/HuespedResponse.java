package com.adriel.autorization.dto;

import com.adriel.autorization.enums.EstadoRegistro;

public record HuespedResponse(
 Long id,
 String nombre,
 String apellido,
 String email,
 String telefono,
 String documento,
 String nacionalidad,
 EstadoRegistro estado
) {}
