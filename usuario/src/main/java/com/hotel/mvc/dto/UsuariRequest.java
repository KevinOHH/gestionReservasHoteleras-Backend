package com.hotel.mvc.dto;

import com.hotel.mvc.enums.Rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuariRequest(

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 5, max = 20, message = "El username debe tener entre 5 y 20 caracteres")
    String username,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
        message = "La contraseña debe contener letras y números"
    )
    String password,

    @NotNull(message = "El rol es obligatorio")
    Rol rol

) {}
