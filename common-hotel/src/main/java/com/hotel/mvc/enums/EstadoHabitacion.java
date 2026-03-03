package com.hotel.mvc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {

    DISPONIBLE(1L, "Lista para asignarse"),
    OCUPADA(2L, "Asignada a una reserva"),
    LIMPIEZA(3L, "En limpieza"),
    MANTENIMIENTO(4L, "En reparación");

    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion fromCodigo(Long codigo) {
        for (EstadoHabitacion estado : EstadoHabitacion.values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de habitación no válido: " + codigo);
    }

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
    
    public void validarCambio(EstadoHabitacion nuevoEstado) {
        
        if (this == OCUPADA && nuevoEstado == DISPONIBLE) {
            throw new IllegalArgumentException("No se puede pasar de OCUPADA a DISPONIBLE directamente");
        }
        if (this == LIMPIEZA && nuevoEstado == OCUPADA) {
            throw new IllegalArgumentException("No se puede asignar una habitación en limpieza a OCUPADA");
        }
        
    }
}