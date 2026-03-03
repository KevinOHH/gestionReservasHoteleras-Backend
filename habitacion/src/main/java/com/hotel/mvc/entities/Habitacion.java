
package com.hotel.mvc.entities;
import java.math.BigDecimal;

import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.TipoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HABITACIONES")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

<<<<<<< HEAD
    @Column(name= "NUMERO" ,nullable = false)
    @Min(value = 1)
    private Integer numero; 

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Positive
    @Column(name = "PRECIO",nullable = false)
    private BigDecimal precio;

    @Min(value = 1)
    @Column(name = "CAPACIDAD",nullable = false)
=======
    @Column(nullable = false, unique = true)
    private Integer numero; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoHabitacion tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
>>>>>>> 7d838ab76056ace9464c77c456a43ded8354e0d6
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", nullable = false)
    private EstadoHabitacion estadoHabitacion;
    
    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
    @Column(name = "ESTADO", nullable = false)
    private EstadoRegistro estadoRegistro;
    
=======
    @Column(name = "estado", nullable = false)
    private EstadoRegistro estado;
>>>>>>> 7d838ab76056ace9464c77c456a43ded8354e0d6
}