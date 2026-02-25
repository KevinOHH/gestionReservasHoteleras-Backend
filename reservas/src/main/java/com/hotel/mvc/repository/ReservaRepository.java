package com.hotel.mvc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.mvc.entities.Reserva;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.EstadoReserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	List<Reserva> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	//boolean existsByIdHabitacionAndEstadoRegistroAndEstadoHabitacionIn(
	///		Long idHabitacion, EstadoRegistro estadoRegistro, List<EstadoHabitacion> estadoHabitacion);
	
	boolean existsByIdHabitacionAndEstadoRegistroAndEstadoReservaIn(
			Long idHabitacion, EstadoRegistro estadoRegistro, List<EstadoReserva> estadoReservas);
	
	boolean existsByIdHabitacionAndEstadoRegistroAndEstadoReservaInAndIdNot(
		    Long idHabitacion, 
		    EstadoRegistro estadoRegistro, 
		    List<EstadoReserva> estados, 
		    Long idReservaAExcluir
		);
	
	boolean existsByIdHuespedAndEstadoRegistro(
			Long idHuesped, EstadoRegistro estadoRegistro);
}
