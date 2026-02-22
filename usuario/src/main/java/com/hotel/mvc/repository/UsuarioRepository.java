package com.hotel.mvc.repository;

import com.hotel.mvc.entities.Usuario;
import com.hotel.mvc.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findAllByEstado(EstadoRegistro estado);

    Optional<Usuario> findByIdAndEstado(Long id, EstadoRegistro estado);

    boolean existsByUsernameAndEstado(String username, EstadoRegistro estado);

    boolean existsByUsernameAndEstadoAndIdNot(String username, EstadoRegistro estado, Long id);
}
