package com.hotel.mvc.service;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.mapper.HabitacionMapper;
import com.hotel.mvc.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        if (habitacionRepository.existsByNumeroAndEstado(request.numero(), "ACTIVO"))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una habitación activa con el número " + request.numero());

        Habitacion habitacion = habitacionMapper.toEntity(request);
        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public List<HabitacionResponse> listar() {
        return habitacionRepository.findAllByEstado("ACTIVO")
                .stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    @Override
    public HabitacionResponse obtenerPorId(Long id) {
        return habitacionMapper.toResponse(getActivaOrThrow(id));
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = getActivaOrThrow(id);

        if (habitacionRepository.existsByNumeroAndEstadoAndIdNot(request.numero(), "ACTIVO", id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una habitación activa con el número " + request.numero());

        if (habitacion.getEstadoHabitacion().estaOcupada())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede modificar una habitación OCUPADA");

        habitacionMapper.updateEntity(habitacion, request);
        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public void eliminar(Long id) {
        Habitacion habitacion = getActivaOrThrow(id);

        if (!habitacion.getEstadoHabitacion().permiteEliminacion())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar una habitación OCUPADA");

        habitacion.setEstado("ELIMINADO");
        habitacionRepository.save(habitacion);
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Habitacion getActivaOrThrow(Long id) {
        return habitacionRepository.findByIdAndEstado(id, "ACTIVO")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Habitación con id " + id + " no encontrada o eliminada"));
    }
}