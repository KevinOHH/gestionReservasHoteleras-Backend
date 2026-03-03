package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper <HabitacionRequest, HabitacionResponse, Habitacion> {

    
    public void updateEntity(Habitacion habitacion, HabitacionRequest request) {
        
       
    }


	@Override
	public HabitacionResponse entityToResponse(Habitacion habitacion) {
		return new HabitacionResponse(
        		habitacion.getId(),
                habitacion.getNumero(),           
                habitacion.getTipoHabitacion(),
                habitacion.getPrecio(),
                habitacion.getCapacidad(),
                habitacion.getEstadoHabitacion(),
                habitacion.getEstadoRegistro()
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
	public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion habitacion) {
		habitacion.setNumero(request.numero());
        habitacion.setTipoHabitacion(request.tipoHabitacion());
        habitacion.setPrecio(request.precio());
        habitacion.setCapacidad(request.capacidad());
        
        return habitacion;
	}
}