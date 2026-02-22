package com.hotel.mvc.service;

import com.hotel.mvc.dto.UsuarioRequest;
import com.hotel.mvc.dto.UsuarioResponse;
import com.hotel.mvc.entities.Usuario;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.exceptions.ConflictException;
import com.hotel.mvc.exceptions.ResourceNotFoundException;
import com.hotel.mvc.mapper.UsuarioMapper;
import com.hotel.mvc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAllByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    public UsuarioResponse obtenerPorId(Long id) {
        return usuarioMapper.toResponse(obtenerUsuarioActivoOLanzarExcepcion(id));
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        validarUsernameUnico(request.username(), null);
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse actualizar(UsuarioRequest request, Long id) {
        Usuario usuario = obtenerUsuarioActivoOLanzarExcepcion(id);
        validarUsernameUnico(request.username(), id);
        usuarioMapper.updateEntity(request, usuario);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuarioActivoOLanzarExcepcion(id);
        usuario.setEstado(EstadoRegistro.ELIMINADO);
        usuarioRepository.save(usuario);
    }

    private Usuario obtenerUsuarioActivoOLanzarExcepcion(Long id) {
        return usuarioRepository.findByIdAndEstado(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con ID: " + id));
    }

    private void validarUsernameUnico(String username, Long excludeId) {
        boolean existe = (excludeId == null)
                ? usuarioRepository.existsByUsernameAndEstado(username, EstadoRegistro.ACTIVO)
                : usuarioRepository.existsByUsernameAndEstadoAndIdNot(username, EstadoRegistro.ACTIVO, excludeId);

        if (existe) {
            throw new ConflictException("Ya existe un usuario activo con el username: " + username);
        }
    }
}