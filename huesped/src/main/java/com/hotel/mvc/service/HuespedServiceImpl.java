 package com.hotel.mvc.service;

import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.entities.Huesped;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.TipoDocumento;
import com.hotel.mvc.exceptions.ConflictException;
import com.hotel.mvc.exceptions.ResourceNotFoundException;
import com.hotel.mvc.mapper.HuespedMapper;
import com.hotel.mvc.repository.HuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        validarUnicidad(request, null);
        Huesped huesped = huespedMapper.toEntity(request);
        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }


    private void validarUnicidad(HuespedRequest request, Long idExcluir) {
        if (idExcluir == null) {
            if (huespedRepository.existsByEmailAndEstado(request.email(), EstadoRegistro.ACTIVO))
                throw new ConflictException("Ya existe un huésped con ese email");
            if (huespedRepository.existsByTelefonoAndEstado(request.telefono(), EstadoRegistro.ACTIVO))
                throw new ConflictException("Ya existe un huésped con ese teléfono");
        } else {
            if (huespedRepository.existsByEmailAndEstadoAndIdNot(request.email(), EstadoRegistro.ACTIVO, idExcluir))
                throw new ConflictException("Ya existe un huésped con ese email");
            if (huespedRepository.existsByTelefonoAndEstadoAndIdNot(request.telefono(), EstadoRegistro.ACTIVO, idExcluir))
                throw new ConflictException("Ya existe un huésped con ese teléfono");
        }
    }
    
    @Override
    public List<HuespedResponse> listar() {
        return huespedRepository.findAllByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    @Override
    public HuespedResponse obtenerPorId(Long id) {
        return huespedMapper.toResponse(getOrThrow(id));
    }

    @Override
    public HuespedResponse findByHuespedId(Long id) {
        return huespedMapper.toResponse(
            huespedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Huésped con id " + id + " no encontrado"))
        );
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = getOrThrow(id);
        validarUnicidad(request, id);
        huespedMapper.updateEntity(huesped, request);
        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = getOrThrow(id);
        huesped.setEstado(EstadoRegistro.ELIMINADO);
        huespedRepository.save(huesped);
    }

    private Huesped getOrThrow(Long id) {
        return huespedRepository.findByIdAndEstado(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Huésped con id " + id + " no encontrado"));
    }
}