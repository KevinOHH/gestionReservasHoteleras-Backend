package com.hotel.mvc.mappers;

import org.springframework.stereotype.Component;

import com.hotel.mvc.dto.DatosHabitacion;
import com.hotel.mvc.dto.DatosHuesped;
import com.hotel.mvc.dto.ReservaRequest;
import com.hotel.mvc.dto.ReservaResponse;
import com.hotel.mvc.dto.habitacion.HabitacionResponse;
import com.hotel.mvc.dto.huesped.HuespedResponse;
import com.hotel.mvc.entities.Reserva;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.EstadoReserva;
import com.hotel.mvc.mapper.CommonMapper;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {

	@Override
	public ReservaResponse entityToResponse(Reserva entity) {
		if (entity == null) return null; 
		
		return new ReservaResponse(
				entity.getId(),
				null,
				null,
				entity.getFechaEntrada(),
				entity.getFechaSalida(), 
				entity.getEstadoReserva().name());
	}
	
	public ReservaResponse entityToResponse(Reserva entity, HuespedResponse huesped, HabitacionResponse habitacion) {
		if (entity == null) return null; 
		
		return new ReservaResponse(
				entity.getId(), 
				datosHuespedFromHuespedResponse(huesped), 
				datosHabitacionFromHabitacionResponse(habitacion),
				entity.getFechaEntrada(), 
				entity.getFechaSalida(), 
				entity.getEstadoReserva().name());
	}

	@Override
	public Reserva requestToEntity(ReservaRequest request) {
		if (request == null) return null;
		
		return Reserva.builder()
				.idHuesped(request.idHuesped())
				.idHabitacion(request.idHabitacion())
				.fechaEntrada(request.fechaEntrada())
				.fechaSalida(request.fechaSalida())
				.estadoReserva(EstadoReserva.CONFIRMADA)
				.estadoRegistro(EstadoRegistro.ACTIVO)
				.build();
	}

	@Override
	public Reserva updateEntityFromRequest(ReservaRequest request, Reserva entity) {
		if (request == null || entity == null) return null;
		
		//entity.setIdHuesped(request.idHuesped());
		//entity.setIdHabitacion(request.idHabitacion());
		entity.setFechaEntrada(request.fechaEntrada());
		entity.setFechaSalida(request.fechaSalida());
		
		return entity;
	}
	/*
	public Reserva updateEntityFromRequest(ReservaRequest request, Reserva entity, EstadoReserva estadoReserva) {
		if (request == null || entity == null) return null;
		
		updateEntityFromRequest(request, entity);
		entity.setEstadoReserva(estadoReserva);
		
		return entity;
	}*/
	
	private DatosHuesped datosHuespedFromHuespedResponse(HuespedResponse huesped) {
		if (huesped == null) return null;
		
		return new DatosHuesped(
				huesped.nombre(),
				huesped.email(),
				huesped.telefono(),
				huesped.tipoDocumento(),
				huesped.nacionalidad()
				);
	}
	
	private DatosHabitacion datosHabitacionFromHabitacionResponse(HabitacionResponse habitacion) {
		if (habitacion == null) return null;
		
		return new DatosHabitacion(
				habitacion.numero(),
				habitacion.tipo(),
				habitacion.precio(),
				habitacion.capacidad()
				);
	}

}
