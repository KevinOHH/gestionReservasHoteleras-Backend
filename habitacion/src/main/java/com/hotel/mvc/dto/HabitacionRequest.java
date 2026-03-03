package com.hotel.mvc.dto;

import java.math.BigDecimal;

import com.hotel.mvc.enums.TipoHabitacion;

import jakarta.validation.constraints.*;

public record HabitacionRequest(

		@NotNull(message = "El número es obligatorio")
		@Min(value = 1, message = "El número de habitación debe ser mayor a 0")
		@Positive(message = "El número debe ser positivo")
		Integer numero,

        @NotNull(message = "El tipo es obligatorio")
        //@Pattern(regexp = "INDIVIDUAL | DOBLE | SUITE", message = "El tipo debe ser INDIVIDUAL , DOBLE o SUITE")
        TipoHabitacion tipoHabitacion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad

) {}