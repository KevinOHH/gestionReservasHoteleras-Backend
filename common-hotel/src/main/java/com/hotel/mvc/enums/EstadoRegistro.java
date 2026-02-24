package com.hotel.mvc.enums;

import com.hotel.mvc.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoRegistro {

    ACTIVO(1L, "Registro activo"),
    ELIMINADO(2L, "Registro eliminado");

    private final Long codigo;
    private final String descripcion;

    public static EstadoRegistro fromNombre(String nombre) {
        for (EstadoRegistro e : EstadoRegistro.values()) {
            if (e.name().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        throw new ResourceNotFoundException("Estado de registro no válido: '" + nombre +
                "'. Valores aceptados: ACTIVO, ELIMINADO");
    }
}