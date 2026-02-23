package com.hotel.mvc.entities;

import com.hotel.mvc.enums.EstadoHabitacion;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String numero;                  // VARCHAR2(5)

    @Column(nullable = false, length = 50)
    private String tipo;                    // VARCHAR2(50)

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;              // NUMBER(10,2)

    @Column(nullable = false)
    private Integer capacidad;             // NUMBER(3)

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_habitacion", nullable = false, length = 15)
    private EstadoHabitacion estadoHabitacion;  // VARCHAR2(15)

    @Column(nullable = false, length = 10)
    private String estado;                 // VARCHAR2(10)
}