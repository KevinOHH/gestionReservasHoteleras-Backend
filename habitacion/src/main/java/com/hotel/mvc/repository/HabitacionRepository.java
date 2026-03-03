package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    
    //List<Habitacion> findAllByEstadoHabitacion(EstadoHabitacion estadoHabitacion);
    List<Habitacion> findAllByEstado(EstadoRegistro estado);
    List<Habitacion> findAllByEstadoHabitacion(EstadoHabitacion estadoHabitacion);
    
    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    Boolean existsByNumeroHabitacionAndEstadoRegistro(Integer numero, EstadoRegistro estadoRegistro);
    
    Boolean existsByNumeroHabitacionAndEstadoRegistroAndIdNot(Integer numero, EstadoRegistro estadoRegistro, Long id);
    
    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    
    Boolean existsByNumeroAndEstadoHabitacion(Integer numero, EstadoHabitacion estadoHabitacion);

   
    Boolean existsByNumeroAndEstadoHabitacionAndIdNot(Integer numero, EstadoHabitacion estadoHabitacion, Long id);
    
    
}