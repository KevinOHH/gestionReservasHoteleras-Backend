package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

   
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
}