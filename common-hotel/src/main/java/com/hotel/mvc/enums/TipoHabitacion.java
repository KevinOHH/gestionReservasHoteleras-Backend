package com.hotel.mvc.enums;

public enum TipoHabitacion {

    SENCILLA,
    DOBLE,
    SUITE;

    public static TipoHabitacion fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de habitación no puede ser nulo o vacío");
        }

        for (TipoHabitacion t : TipoHabitacion.values()) {
            if (t.name().equalsIgnoreCase(value.trim())) {
                return t;
            }
        }

        throw new IllegalArgumentException("Tipo de habitación inválido: " + value);
    }
}