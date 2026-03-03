package com.hotel.mvc.entities;
import java.math.BigDecimal;

import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.TipoHabitacion;

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
    private Integer capacidad; 

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", nullable = false)
    private EstadoHabitacion estadoHabitacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoRegistro estadoRegistro;
    
}