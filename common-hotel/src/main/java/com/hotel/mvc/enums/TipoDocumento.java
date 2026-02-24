package com.hotel.mvc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {

    INE(1L, "Identificación Nacional Electoral"),
    PASAPORTE(2L, "Pasaporte"),
    LICENCIA(3L, "Licencia de conducir");

    private final Long codigo;
    private final String descripcion;

    public static TipoDocumento fromNombre(String nombre) {
        for (TipoDocumento t : TipoDocumento.values()) {
        	if (t.name().equalsIgnoreCase(nombre)) { 
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de documento no valido: '" + nombre +
        		"'. Valores aceptados: INE, PASAPORTE, LICENCIA");
    }
}