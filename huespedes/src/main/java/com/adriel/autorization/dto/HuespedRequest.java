package com.adriel.autorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    String apellido,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 100, message = "El email no debe superar 100 caracteres")
    String email,

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
        regexp = "^\\d{10}$",
        message = "El teléfono debe tener exactamente 10 dígitos"
    )
    String telefono,

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 50, message = "El documento no debe superar 50 caracteres")
    String documento,

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 50, message = "La nacionalidad no debe superar 50 caracteres")
    String nacionalidad

) {}