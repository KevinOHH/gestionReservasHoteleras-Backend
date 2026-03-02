package com.hotel.mvc.repositories;

import com.hotel.mvc.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    // Buscar solo ACTIVOS
    Optional<Usuario> findByIdAndEstadoRegistro(Long id, String estadoRegistro);
    List<Usuario> findAllByEstadoRegistro(String estadoRegistro);

    boolean existsByUsernameAndEstadoRegistro(String username, String estadoRegistro);
    boolean existsByUsernameAndEstadoRegistroAndIdNot(String username, String estadoRegistro, Long id);
}