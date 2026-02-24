package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Huesped;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.TipoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    List<Huesped> findAllByEstado(EstadoRegistro estado);

    Optional<Huesped> findById(Long id);
    
    Optional<Huesped> findByIdAndEstado(Long id, EstadoRegistro estado);

    boolean existsByEmailAndEstado(String email, EstadoRegistro estado);
    boolean existsByTelefonoAndEstado(String telefono, EstadoRegistro estado);

    boolean existsByEmailAndEstadoAndIdNot(String email, EstadoRegistro estado, Long id);
    boolean existsByTelefonoAndEstadoAndIdNot(String telefono, EstadoRegistro estado, Long id);
}