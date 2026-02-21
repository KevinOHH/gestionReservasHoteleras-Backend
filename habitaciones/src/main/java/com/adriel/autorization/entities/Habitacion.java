package com.adriel.autorization.entities;

import java.math.BigDecimal;

import com.adriel.autorization.enums.EstadoHabitacion;
import com.adriel.autorization.enums.EstadoRegistro;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habitaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false, length = 5)
    private String numero;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_habitacion", nullable = false, length = 15)
    @Builder.Default
    private EstadoHabitacion estadoHabitacion = EstadoHabitacion.DISPONIBLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    @Builder.Default
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}