package com.hotel.mvc.dto.habitacion;

import java.math.BigDecimal;

import com.hotel.mvc.enums.TipoHabitacion;

import jakarta.validation.constraints.*;

public record HabitacionRequest(

        @NotNull(message = "El número es obligatorio")
        @Max(value = 50, message = "El número no puede exceder 3 digitos")
        Integer numero,

        @NotNull(message = "El tipo es obligatorio")
        //@Pattern(regexp = "SENCILLA|DOBLE|SUITE", message = "El tipo debe ser SENCILLA, DOBLE o SUITE")
        //String tipo,
        TipoHabitacion tipo,
        
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad

) {}