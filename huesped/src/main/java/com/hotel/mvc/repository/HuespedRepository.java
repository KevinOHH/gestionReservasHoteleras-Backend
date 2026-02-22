package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    List<Huesped> findAllByEstado(String estado);

    Optional<Huesped> findByIdAndEstado(Long id, String estado);

    boolean existsByEmailAndEstado(String email, String estado);

    boolean existsByTelefonoAndEstado(String telefono, String estado);

    boolean existsByDocumentoAndEstado(String documento, String estado);

    boolean existsByEmailAndEstadoAndIdNot(String email, String estado, Long id);

    boolean existsByTelefonoAndEstadoAndIdNot(String telefono, String estado, Long id);

    boolean existsByDocumentoAndEstadoAndIdNot(String documento, String estado, Long id);
}