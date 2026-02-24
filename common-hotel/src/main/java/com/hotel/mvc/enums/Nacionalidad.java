package com.hotel.mvc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nacionalidad {

    MEXICANA(1L, "Mexicana"),
    ARGENTINA(2L, "Argentina"),
    CHILENA(3L, "Chilena"),
    COLOMBIANA(4L, "Colombiana"),
    PERUANA(5L, "Peruana"),
    ESPAÑOLA(6L, "Española"),
    FRANCESA(7L, "Francesa"),
    ITALIANA(8L, "Italiana"),
    ESTADOUNIDENSE(9L, "Estadounidense"),
    CANADIENSE(10L, "Canadiense");

    private final Long codigo;
    private final String descripcion;

    public static Nacionalidad fromNombre(String nombre) {
        for (Nacionalidad n : Nacionalidad.values()) {
            if (n.name().equalsIgnoreCase(nombre)) {
                return n;
            }
        }
        throw new IllegalArgumentException("Nacionalidad no valida: '" + nombre +
        		"'. Valores aceptadas: MEXICANA, ARGENTINA, CHILENA, COLOMBIA, PERUANA, ESPAÑOLA, FRANCESA, ITALIANA, ESTADUNIDENSE, CANADIENSE");
    }
}