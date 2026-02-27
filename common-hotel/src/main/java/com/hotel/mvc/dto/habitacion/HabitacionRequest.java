package com.hotel.mvc.dto.habitacion;

import java.math.BigDecimal;

import com.hotel.mvc.enums.TipoHabitacion;

import jakarta.validation.constraints.*;

public record HabitacionRequest(

        @NotBlank(message = "El número es obligatorio")
        @Size(max = 5, message = "El número no puede exceder 5 caracteres")
        String numero,

        @NotNull(message = "El tipo es obligatorio")
        TipoHabitacion tipoHabitacion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad

) {}