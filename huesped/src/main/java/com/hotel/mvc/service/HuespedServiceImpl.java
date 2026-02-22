package com.hotel.mvc.service;

import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.entities.Huesped;
import com.hotel.mvc.mapper.HuespedMapper;
import com.hotel.mvc.repository.HuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        Huesped huesped = huespedMapper.toEntity(request);
        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }

    @Override
    public List<HuespedResponse> listar() {
        return huespedRepository.findAll()
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
        return obtenerPorId(id);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = getOrThrow(id);
        huespedMapper.updateEntity(huesped, request);
        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }

    @Override
    public void eliminar(Long id) {
        huespedRepository.deleteById(id);
    }

    private Huesped getOrThrow(Long id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Huésped con id " + id + " no encontrado"));
    }
}