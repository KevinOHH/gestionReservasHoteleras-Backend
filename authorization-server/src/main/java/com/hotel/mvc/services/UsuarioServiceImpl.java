package com.hotel.mvc.services;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import com.hotel.mvc.entities.Rol;
import com.hotel.mvc.entities.Usuario;
import com.hotel.mvc.mappers.UsuarioMapper;
import com.hotel.mvc.repositories.RolRepository;
import com.hotel.mvc.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        log.info("Listando usuarios ACTIVOS");
        return usuarioRepository.findAllByEstadoRegistro("ACTIVO")
                .stream()
                .map(usuarioMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        log.info("Buscando usuario con id {}", id);
        return usuarioMapper.entityToResponse(getOrThrow(id));
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        log.info("Registrando usuario {}", request.username());

        // Unicidad solo entre ACTIVOS
        if (usuarioRepository.existsByUsernameAndEstadoRegistro(request.username(), "ACTIVO")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un usuario activo con el username: " + request.username());
        }

        Set<Rol> roles = resolverRoles(request.roles());
        Usuario usuario = usuarioMapper.requestToEntity(
                request, passwordEncoder.encode(request.password()), roles);

        return usuarioMapper.entityToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        log.info("Actualizando usuario con id {}", id);
        Usuario usuario = getOrThrow(id);

        // Unicidad excluyendo el propio registro
        if (usuarioRepository.existsByUsernameAndEstadoRegistroAndIdNot(
                request.username(), "ACTIVO", id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un usuario activo con el username: " + request.username());
        }

        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRoles(resolverRoles(request.roles()));

        return usuarioMapper.entityToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse eliminar(Long id) {
        log.info("Eliminando (soft delete) usuario con id {}", id);
        Usuario usuario = getOrThrow(id);
        usuario.setEstadoRegistro("ELIMINADO");
        return usuarioMapper.entityToResponse(usuarioRepository.save(usuario));
    }

    // ── helpers ──────────────────────────────────────────────

    private Usuario getOrThrow(Long id) {
        return usuarioRepository.findByIdAndEstadoRegistro(id, "ACTIVO")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + id));
    }

    private Set<Rol> resolverRoles(Set<String> nombres) {
        return nombres.stream()
                .map(nombre -> {
                    // Acepta "USER" o "ROLE_USER" indistintamente
                    String normalizado = nombre.startsWith("ROLE_") ? nombre : "ROLE_" + nombre;
                    return rolRepository.findByNombre(normalizado)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Rol no válido: " + nombre + ". Use ROLE_ADMIN o ROLE_USER"));
                })
                .collect(Collectors.toSet());
    }
}