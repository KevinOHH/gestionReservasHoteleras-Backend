package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findAllByEstado(String estado);

    Optional<Habitacion> findByIdAndEstado(Long id, String estado);

    boolean existsByNumeroAndEstado(String numero, String estado);

    boolean existsByNumeroAndEstadoAndIdNot(String numero, String estado, Long id);
}