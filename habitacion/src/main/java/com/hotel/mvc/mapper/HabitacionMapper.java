package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
<<<<<<< HEAD
<<<<<<< HEAD
import com.hotel.mvc.enums.EstadoRegistro;
=======

>>>>>>> 7d838ab76056ace9464c77c456a43ded8354e0d6
=======
import com.hotel.mvc.enums.EstadoRegistro;
>>>>>>> 03f6f9d96e242720617f02732a9723b996cab4a4

import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper <HabitacionRequest, HabitacionResponse, Habitacion> {

   
<<<<<<< HEAD
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
=======
    public Habitacion toEntity(HabitacionRequest request) {
        return Habitacion.builder()
                .numero(request.numero())                
                .tipo(request.tipoHabitacion())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .estado(EstadoRegistro.ACTIVO)
                .build();
    }

    
    public HabitacionResponse toResponse(Habitacion habitacion) {
        return new HabitacionResponse(
        		habitacion.getId(),
                habitacion.getNumero(),           
                habitacion.getTipo(),
                habitacion.getPrecio(),
                habitacion.getCapacidad(),
                habitacion.getEstadoHabitacion(),
                habitacion.getEstado()
        );
    }

    
    public void updateEntity(Habitacion habitacion, HabitacionRequest request) {
        habitacion.setNumero(request.numero());
        habitacion.setTipo(request.tipoHabitacion());
        habitacion.setPrecio(request.precio());
        habitacion.setCapacidad(request.capacidad());
       
    }
>>>>>>> 7d838ab76056ace9464c77c456a43ded8354e0d6
}