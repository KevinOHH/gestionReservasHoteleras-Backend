package com.hotel.mvc.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import java.util.Set;

public record UsuarioRequest(
        @NotBlank(message = "El username es requerido")
        @Size(min = 5, max = 20, message = "El username debe tener entre 5 y 20 caracteres")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$",
                 message = "La contraseña debe contener letras y números")
        String password,

        @NotNull(message = "El rol es requerido")
        @Size(min = 1, message = "Debe asignar al menos un rol")
        Set<String> roles
) {}
