package com.hotel.mvc.entities;

import java.time.LocalDateTime;

import com.hotel.mvc.enums.EstadoReserva;
import com.hotel.mvc.enums.EstadoRegistro;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESERVAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Reserva {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;
	
	@Column(name = "ID_HUESPED", nullable = false)
	private Long idHuesped;
	
	@Column(name = "ID_HABITACION", nullable = false)
	private Long idHabitacion;
	
	@Column(name = "FECHA_ENTRADA", nullable = false)
	private LocalDateTime fechaEntrada;
	
	@Column(name = "FECHA_SALIDA", nullable = false)
	private LocalDateTime fechaSalida;
	
	@Column(name = "ESTADO_RESERVA", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoReserva estadoReserva;
	
	@Column(name = "ESTADO", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
	private EstadoRegistro estadoRegistro;
	
}
