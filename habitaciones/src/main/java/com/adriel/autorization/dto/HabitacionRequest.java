package com.adriel.autorization.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HabitacionRequest(

 @NotNull(message = "El número de habitación es obligatorio")
 @Min(value = 1, message = "El número de habitación debe ser mayor a 0")
 Integer numero,

 @NotBlank(message = "El tipo es obligatorio")
 String tipo,

 @NotNull(message = "El precio es obligatorio")
 @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
 BigDecimal precio,

 @NotNull(message = "La capacidad es obligatoria")
 @Min(value = 1, message = "La capacidad mínima es 1")
 Integer capacidad

) {}