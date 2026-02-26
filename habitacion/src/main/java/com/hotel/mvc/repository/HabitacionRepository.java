package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    
    List<Habitacion> findAllByEstadoHabitacion(EstadoHabitacion estadoHabitacion);
    
    List<Habitacion>findByEstadoRegistro(EstadoRegistro estadoRegistro);

    
    Optional<Habitacion> findByIdAndEstadoHabitacion(Long id, EstadoHabitacion estadoHabitacion);

    
    boolean existsByNumeroAndEstadoHabitacion(Integer numero, EstadoHabitacion estadoHabitacion);

   
    boolean existsByNumeroAndEstadoHabitacionAndIdNot(Integer numero, EstadoHabitacion estadoHabitacion, Long id);
}