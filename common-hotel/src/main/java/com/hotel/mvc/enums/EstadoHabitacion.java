package com.hotel.mvc.enums;

public enum EstadoHabitacion {
    DISPONIBLE, OCUPADA, LIMPIEZA, MANTENIMIENTO;

    public boolean puedeAsignarse() {
        return this == DISPONIBLE;
    }

    public boolean estaOcupada() {
        return this == OCUPADA;
    }

    public boolean permiteEliminacion() {
        return this != OCUPADA;
    }

    public boolean permiteCambioManualADisponible() {
        return this != OCUPADA;
    }
}
