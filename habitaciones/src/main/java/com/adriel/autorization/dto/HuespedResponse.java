package com.adriel.autorization.dto;

import java.math.BigDecimal;
import com.adriel.autorization.enums.EstadoRegistro;
import com.adriel.autorization.enums.EstadoHabitacion;

public record HuespedResponse(
 Long id,
 Integer numero,
 String tipo,
 BigDecimal precio,
 Integer capacidad,
 EstadoHabitacion estadoHabitacion,
 EstadoRegistro estado
) {}
