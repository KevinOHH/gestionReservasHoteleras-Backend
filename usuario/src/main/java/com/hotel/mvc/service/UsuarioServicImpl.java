package com.hotel.mvc.service;

import com.hotel.mvc.dto.UsuariRequest;
import com.hotel.mvc.dto.UsuariResponse;
import com.hotel.mvc.entities.Usuariop;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.exceptions.ConflictException;
import com.hotel.mvc.exceptions.ResourceNotFoundException;
import com.hotel.mvc.mapper.UsuarioMappr;
import com.hotel.mvc.repository.UsuariRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServicImpl implements UsuarioServic {

    private final UsuariRepository usuarioRepository;
    private final UsuarioMappr usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuariResponse> listar() {
        return usuarioRepository.findAllByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    public UsuariResponse obtenerPorId(Long id) {
        return usuarioMapper.toResponse(obtenerUsuarioActivoOLanzarExcepcion(id));
    }

    @Override
    public UsuariResponse registrar(UsuariRequest request) {
        validarUsernameUnico(request.username(), null);
        Usuariop usuario = usuarioMapper.toEntity(request);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuariResponse actualizar(UsuariRequest request, Long id) {
        Usuariop usuario = obtenerUsuarioActivoOLanzarExcepcion(id);
        validarUsernameUnico(request.username(), id);
        usuarioMapper.updateEntity(request, usuario);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuariop usuario = obtenerUsuarioActivoOLanzarExcepcion(id);
        usuario.setEstado(EstadoRegistro.ELIMINADO);
        usuarioRepository.save(usuario);
    }

    private Usuariop obtenerUsuarioActivoOLanzarExcepcion(Long id) {
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