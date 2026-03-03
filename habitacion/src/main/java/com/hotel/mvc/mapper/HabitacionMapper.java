package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper <HabitacionRequest, HabitacionResponse, Habitacion> {

   
	@Override
	public HabitacionResponse entityToResponse(Habitacion entity) {
		if(entity == null) {return null;}
		
		
		return new HabitacionResponse(
					entity.getId(),
					entity.getNumero(),
					entity.getTipoHabitacion(),
					entity.getPrecio(),
					entity.getCapacidad(),
					entity.getEstadoHabitacion()
				);
	}

	@Override
	public Habitacion requestToEntity(HabitacionRequest request) {
		return Habitacion.builder()
				.numero(request.numero())
	            .tipoHabitacion(request.tipoHabitacion())
	            .precio(request.precio())
	            .capacidad(request.capacidad())
	            .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
	            .estadoRegistro(EstadoRegistro.ACTIVO)
	            .build();
	}

	@Override
	public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
		if(entity == null || request == null) return null;
		entity.setNumero(request.numero());
		entity.setTipoHabitacion(request.tipoHabitacion());
		entity.setPrecio(request.precio());
		entity.setCapacidad(request.capacidad());
		entity.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
		return entity;
	}
	
	public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity, Long idEstadoHabitacion) {
		if(entity == null || request == null) return null;
		updateEntityFromRequest(request, entity);
		entity.setEstadoHabitacion(EstadoHabitacion.fromCodigo(idEstadoHabitacion));
		return entity;
	}
}