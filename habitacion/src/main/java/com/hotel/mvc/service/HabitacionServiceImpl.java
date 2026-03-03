package com.hotel.mvc.service;

import com.hotel.mvc.client.ReservaClient;
import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.entities.Habitacion;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.exceptions.NegocioException;
import com.hotel.mvc.mapper.HabitacionMapper;
import com.hotel.mvc.repository.HabitacionRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hotel.mvc.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final ReservaClient reservaClient;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando habitaciones activas");
        return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(habitacionMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        return habitacionMapper.entityToResponse(getHabitacionOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorIdSinEstado(Long id) {
        return habitacionMapper.entityToResponse(getHabitacionOrThrowSinEstado(id));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando habitación: {}", request);
        validarDatos(request);
        validarNumeroHabitacion(request.numero());

        Habitacion habitacion = habitacionMapper.requestToEntity(request);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO);
        habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);

        habitacionRepository.save(habitacion);
        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        log.info("Actualizando habitación con id: {}", id);
        Habitacion habitacion = getHabitacionOrThrow(id);

        validarDatos(request);
        validarNumeroHabitacionActualizar(request.numero(), id);

        if (habitacion.getEstadoHabitacion() != EstadoHabitacion.DISPONIBLE) {
            throw new NegocioException("No se puede modificar una habitación que no está disponible");
        }

        habitacionMapper.updateEntityFromRequest(request, habitacion);
        habitacionRepository.save(habitacion);

        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse modificarEstadoHabitacion(Long idHabitacion, EstadoHabitacion nuevoEstado) {
        Habitacion habitacion = getHabitacionOrThrow(idHabitacion);

        habitacion.getEstadoHabitacion().validarCambio(nuevoEstado);

        habitacion.setEstadoHabitacion(nuevoEstado);
        habitacionRepository.save(habitacion);

        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        Habitacion habitacion = getHabitacionOrThrow(id);
        validarEstadoHabitacion(habitacion);
        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        habitacionRepository.save(habitacion);
    }
    
    @Override
	public HabitacionResponse findByHabitacionId(Long id) {
		return habitacionMapper.entityToResponse(
	            habitacionRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Habitación con id " + id + " no encontrada"))
	        );
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

    		    
    		    return habitacionMapper.entityToResponse(habitacionActualizada);
    			
    }

    @Override
    public void validarHabitacion(Long idHabitacion) {
        Habitacion habitacion = getHabitacionOrThrow(idHabitacion);
        validarEstadoHabitacion(habitacion);
    }

    @Override
    public void cambiarHabitacion(Long idHabitacionActual, Long idHabitacionNueva) {
        Habitacion habitacionActual = getHabitacionOrThrow(idHabitacionActual);
        Habitacion habitacionNueva = getHabitacionOrThrow(idHabitacionNueva);

        habitacionActual.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
        habitacionNueva.setEstadoHabitacion(EstadoHabitacion.OCUPADA);

        habitacionRepository.save(habitacionActual);
        habitacionRepository.save(habitacionNueva);
    }

    // ------------------- MÉTODOS PRIVADOS -------------------

    private Habitacion getHabitacionOrThrow(Long id) {
        log.info("Buscando habitación activa con id: {}", id);
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con id: " + id));
    }

    private Habitacion getHabitacionOrThrowSinEstado(Long id) {
        log.info("Buscando habitación sin estado con id: {}", id);
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con id: " + id));
    }

    private void validarNumeroHabitacion(Integer numero) {
        if (habitacionRepository.existsByNumeroAndEstadoRegistro(numero, EstadoRegistro.ACTIVO)) {
            throw new NegocioException("Ya existe un número de habitación: " + numero);
        }
    }

    private void validarNumeroHabitacionActualizar(Integer numero, Long id) {
        if (habitacionRepository.existsByNumeroAndEstadoRegistroAndIdNot(numero, EstadoRegistro.ACTIVO, id)) {
            throw new NegocioException("Ya existe un número de habitación: " + numero);
        }
    }

    private void validarDatos(HabitacionRequest request) {
        if (request.numero() <= 0)
            throw new NegocioException("El número debe ser mayor a 0");
        if (request.capacidad() < 1)
            throw new NegocioException("La capacidad mínima es 1");
        if (request.precio() == null || request.precio().compareTo(BigDecimal.ZERO) <= 0)
            throw new NegocioException("El precio debe ser mayor a 0");
        if (request.tipoHabitacion() == null)
            throw new NegocioException("El tipo de habitación es obligatorio");
    }

    private void validarEstadoHabitacion(Habitacion habitacion) {
        if (habitacion.getEstadoHabitacion() != EstadoHabitacion.DISPONIBLE) {
            throw new NegocioException("La habitación está en uso");
        }
    }

    private Habitacion getActivaOrThrow(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Habitación con id " + id + " no encontrada"));
    }
    
	@Override
	@Transactional
	public HabitacionResponse actualizarDisponibilidadHabitacion(Long idHabitacion, Long idDisponibilidad,
			Long idReservaActual) {
		log.info("Cambiando habitacion {} a estado {}", idHabitacion, idDisponibilidad);
		Habitacion habitacion = getActivaOrThrow(idHabitacion);
		
		if (idDisponibilidad.equals(1L)) {
			if (idReservaActual != null) {
				reservaClient.habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(idHabitacion, idReservaActual);
			} else {
				reservaClient.habitacionTieneReservasConfirmadasoEnCurso(idHabitacion);
			}
		}
		
		habitacion.setEstadoHabitacion(EstadoHabitacion.fromCodigo(idDisponibilidad));
		return habitacionMapper.entityToResponse(habitacion);
	}
}