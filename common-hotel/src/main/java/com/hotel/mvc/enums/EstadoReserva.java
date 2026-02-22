package com.hotel.mvc.enums;

public enum EstadoReserva {
    CONFIRMADA, EN_CURSO, FINALIZADA, CANCELADA;

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