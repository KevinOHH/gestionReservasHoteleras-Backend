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

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {

        
        if (request.numero() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número debe ser mayor a 0");

        if (request.capacidad() < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La capacidad mínima es 1");

        if (request.precio() == null || request.precio().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a 0");

        if (request.tipoHabitacion() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de habitación es obligatorio");

        
        if (habitacionRepository.existsByNumeroAndEstadoHabitacion(request.numero(), EstadoHabitacion.DISPONIBLE))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una habitación activa con el número " + request.numero());

        Habitacion habitacion = habitacionMapper.toEntity(request);

        
        habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);

        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public List<HabitacionResponse> listar() {
        return habitacionRepository.findAllByEstadoHabitacion(EstadoHabitacion.DISPONIBLE)
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

        
        if (request.numero() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número debe ser mayor a 0");

        if (request.capacidad() < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La capacidad mínima es 1");

        if (request.precio() == null || request.precio().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a 0");

        if (request.tipoHabitacion() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de habitación es obligatorio");

       
        if (habitacionRepository.existsByNumeroAndEstadoHabitacionAndIdNot(request.numero(), EstadoHabitacion.DISPONIBLE, id))
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

        
        habitacion.setEstadoHabitacion(EstadoHabitacion.LIMPIEZA);
        habitacionRepository.save(habitacion);
    }
    
    
    
    public HabitacionResponse cambiarEstado(Long id, EstadoHabitacion nuevoEstado)
    {
    	Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    			
    		    EstadoHabitacion estado;
    		    try {
    		        estado = EstadoHabitacion.valueOf(nuevoEstado.toString());
    		    } catch (IllegalArgumentException e) {
    		        throw new RuntimeException("Estado inválido. Debe ser ACTIVO o ELIMINADO");
    		    }

    		    
    		    habitacion.setEstadoHabitacion(estado);

    		    
    		    Habitacion habitacionActualizada = habitacionRepository.save(habitacion);

    		    
    		    return habitacionMapper.toResponse(habitacionActualizada);
    			
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Habitacion getActivaOrThrow(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Habitación con id " + id + " no encontrada"));
    }
    
    
}