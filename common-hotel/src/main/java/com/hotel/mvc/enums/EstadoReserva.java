package com.hotel.mvc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {

    CONFIRMADA(1L, "Reserva creada"),
    EN_CURSO(2L, "Check-in realizado"),
    FINALIZADA(3L, "Check-out realizado"),
    CANCELADA(4L, "Reserva cancelada");

    private final Long codigo;
    private final String descripcion;

    public static EstadoReserva fromCodigo(Long codigo) {
        for (EstadoReserva estado : EstadoReserva.values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de reserva no válido: " + codigo);
    }

    public boolean permiteModificarFechaEntrada() {
        return this == CONFIRMADA;
    }

    public boolean permiteModificarFechaSalida() {
        return this == CONFIRMADA || this == EN_CURSO;
    }

    public boolean permiteCancelacion() {
        return this == CONFIRMADA;
    }

    public boolean permiteCheckIn() {
        return this == CONFIRMADA;
    }

    public boolean permiteCheckOut() {
        return this == EN_CURSO;
    }

    public boolean estaFinalizada() {
        return this == FINALIZADA || this == CANCELADA;
    }

    public boolean permiteModificacion() {
        return this == CONFIRMADA || this == EN_CURSO;
    }
}