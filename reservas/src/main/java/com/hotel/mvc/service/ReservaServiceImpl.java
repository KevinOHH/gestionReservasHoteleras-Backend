package com.hotel.mvc.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.mvc.client.HabitacionClient;
import com.hotel.mvc.client.HuespedClient;
import com.hotel.mvc.dto.ReservaRequest;
import com.hotel.mvc.dto.ReservaResponse;
import com.hotel.mvc.dto.habitacion.HabitacionResponse;
import com.hotel.mvc.dto.huesped.HuespedResponse;
import com.hotel.mvc.entities.Reserva;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.EstadoReserva;
import com.hotel.mvc.mappers.ReservaMapper;
import com.hotel.mvc.repository.ReservaRepository;
import com.hotel.mvc.exceptions.EntidadRelacionadaExeception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;
    
    private final HuespedClient huespedClient;
    
    private final HabitacionClient habitacionClient;
    
    @Override
    @Transactional(readOnly = true)
	public List<ReservaResponse> listar() {
		log.info("Listado de todas las reservas activas solicitado");
		return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(reserva -> reservaMapper.entityToResponse(
						reserva,
						//null,
						getHuespedResponseSinEstado(reserva.getIdHuesped()),
						//null
						getHabitacionResponseSinEstado(reserva.getIdHabitacion())
						)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public ReservaResponse obtenerPorId(Long id) {
		Reserva reserva = getReservaOrThrow(id);
		
		return reservaMapper.entityToResponse(
				reserva,
				//null,
				getHuespedResponseSinEstado(reserva.getIdHuesped()),
				//null
				getHabitacionResponseSinEstado(reserva.getIdHabitacion())
				);
	}

	@Override
	@Transactional
	public ReservaResponse registrar(ReservaRequest request) {
		log.info("Registrando nueva reservacion {}", request);
		
		if (!request.fechaEntrada().isBefore(request.fechaSalida())) {
	        throw new EntidadRelacionadaExeception("La fecha de entrada debe ser anterior a la de salida.");
	    }
		
		validarHabitacionSinReservaActivas(request.idHabitacion());
		
		HuespedResponse huesped = getHuespedResponse(request.idHuesped());
		HabitacionResponse habitacion = getHabitacionResponse(request.idHabitacion());
		
		Reserva reserva = reservaRepository.save(reservaMapper.requestToEntity(request));
		
		aplicarReglaDisponibilidadHabitacion(reserva.getIdHabitacion(), reserva.getEstadoReserva(), reserva.getId());
		
		log.info("Reserva refistrada exitosamente: {}", reserva);
		return reservaMapper.entityToResponse(reserva, huesped, habitacion);
	}

	@Override
	@Transactional
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		Reserva reserva = getReservaOrThrow(id);
		log.info("Actualizando reserva: {}", reserva.getId());
		
		if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA || reserva.getEstadoReserva() == EstadoReserva.FINALIZADA) {
			throw new EntidadRelacionadaExeception("No se puede modificar una reserva FINALIZADA O CANCELADA");
		}
		
		if (!request.fechaEntrada().isBefore(request.fechaSalida())) {
	        throw new EntidadRelacionadaExeception("La fecha de entrada debe ser anterior a la de salida.");
	    }
		
		if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA) {
			reservaMapper.updateEntityFromRequest(request, reserva);
		}
		
		if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO) {
			validarRestriccionesEnCurso(reserva, request);
			reserva.setFechaSalida(request.fechaSalida());
		}
		//EstadoReserva estadoNuevo = EstadoReserva.fromCodigo(request.idEstadoReserva());
		
		HuespedResponse huesped = getHuespedResponse(request.idHuesped());
		///////HabitacionResponse habitacion = getHabitacionResponse(idHabitacionNueva);
		HabitacionResponse habitacion = getHabitacionResponse(request.idHabitacion());
		
		reservaRepository.save(reserva);
		
		log.info("Reserva actualizada exitosamente: {}", reserva);
		//return reservaMapper.entityToResponse(reserva, huesped, habitacion);
		return reservaMapper.entityToResponse(reserva, huesped, habitacion);
	}

	@Override
	public void eliminar(Long id) {
		Reserva reserva = getReservaOrThrow(id);
		
		log.info("Eliminando reserva con id: {}", id);
		
		if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO) {
			throw new NoSuchElementException("No se puede eliminar una reservación " +
					EstadoReserva.EN_CURSO.getDescripcion()); 
		}
		
		aplicarReglaDisponibilidadHabitacion(reserva.getIdHabitacion(), EstadoReserva.CANCELADA, reserva.getId());
		
		reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		
		log.info("Reserva con id {} ha sido marcada como eliminada", id);
	}
	
	private Reserva getReservaOrThrow(Long id) {
		log.info("Buscando reserva activa con id: {}", id);
		
		return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
				.orElseThrow(() -> new NoSuchElementException("Reserva activa no encontrada con el id: " + id));
	}
	
	private HuespedResponse getHuespedResponse(Long idHuesped) {
		return huespedClient.obtenerHuespedPorId(idHuesped);
	}

	private HuespedResponse getHuespedResponseSinEstado(Long idHuesped) {
		return huespedClient.obtenerHuespedPorIdSinEstado(idHuesped);
	}
	
	private HabitacionResponse getHabitacionResponse(Long idHabitacion) {
		return habitacionClient.obtenerHabitacionPorId(idHabitacion);
	}
	
	private HabitacionResponse getHabitacionResponseSinEstado(Long idHabitacion) {
		return habitacionClient.obtenerHabitacionPorIdSinEstado(idHabitacion);
	}
	
	private void validarHabitacionSinReservaActivas(Long idHabitacion) {
		boolean tieneReservaActiva = reservaRepository.existsByIdHabitacionAndEstadoRegistroAndEstadoReservaIn(
				idHabitacion, 
				EstadoRegistro.ACTIVO, 
				List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO));
		
		if (tieneReservaActiva) {
			throw new EntidadRelacionadaExeception("La habitacion ya tiene una reservación activa CONFIRMADA o EN_CURSO");
		}
	}
	
	private void aplicarReglaDisponibilidadHabitacion(Long idHabitacion, EstadoReserva nuevoEstadoReserva, Long idReservaActual) {
	    EstadoHabitacion nuevaDisponibilidad = switch (nuevoEstadoReserva) {
	        case EN_CURSO, CONFIRMADA -> EstadoHabitacion.OCUPADA;
	        case FINALIZADA, CANCELADA -> EstadoHabitacion.DISPONIBLE;
	    };

	    log.info("Reserva en estado {}, cambiando habitacion con id {} a: {}", 
	    		nuevoEstadoReserva, idHabitacion, nuevaDisponibilidad.getDescripcion());
	    
	    habitacionClient.actualizarDisponibilidad(idHabitacion, nuevaDisponibilidad.getCodigo(), idReservaActual);
	}

	@Override
	public void cambiarEstadoReserva(Long idReserva, Long idEstado) {
		log.info("Cambiando estado de la reserva con id: {} al nuevo estado con id: {}", idReserva, idEstado);
		
		Reserva reserva = getReservaOrThrow(idReserva);
		
		EstadoReserva nuevoEstado = EstadoReserva.fromCodigo(idEstado);
		
		validarTransicionEstadoReserva(reserva.getEstadoReserva(), nuevoEstado);
		
		aplicarReglaDisponibilidadHabitacion(reserva.getIdHabitacion(), nuevoEstado, idReserva);
		
		reserva.setEstadoReserva(nuevoEstado);
		reservaRepository.save(reserva);
		
		log.info("Estado de la reserva {} actualizado correctamente a {}", idReserva, nuevoEstado.getDescripcion());
	}
	
	private void validarTransicionEstadoReserva(EstadoReserva estadoReservaActual, EstadoReserva estadoReservaNuevo) {
		if (estadoReservaActual == estadoReservaNuevo) return;
		
		boolean esValido = switch (estadoReservaActual) {
			case CONFIRMADA -> estadoReservaNuevo == EstadoReserva.EN_CURSO || estadoReservaNuevo == EstadoReserva.CANCELADA;
			case EN_CURSO -> estadoReservaNuevo == EstadoReserva.FINALIZADA;
			case FINALIZADA, CANCELADA -> false;
			default -> false;
		};
		
		if (!esValido) {
	        throw new EntidadRelacionadaExeception("Transición de estado no permitida: de " + estadoReservaActual.getDescripcion() + " a " + estadoReservaNuevo.getDescripcion());
	    }
	}

	@Override
	public void habitacionTieneReservasConfirmadasoEnCurso(Long idHabitacion) {
		log.info("Validando reservaciones activas para la habitación con id: {}", idHabitacion);
		
		boolean tieneCitasActivas = reservaRepository.existsByIdHabitacionAndEstadoRegistroAndEstadoReservaIn(
	            idHabitacion, 
	            EstadoRegistro.ACTIVO, 
	            List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO)
	    );
		
		if (tieneCitasActivas) {
			throw new EntidadRelacionadaExeception("No se puede modificar la habitación porque tiene reservación "
					+ EstadoReserva.CONFIRMADA + " o " + EstadoReserva.EN_CURSO);
		}
	}

	@Override
	public void habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(Long idHabitacion,
			Long idReservacionActual) {
		log.info("Validando reservaciones activas para la habitación con id: {}", idHabitacion);
		
		boolean tieneOtrasReservacionoesActivas = reservaRepository.existsByIdHabitacionAndEstadoRegistroAndEstadoReservaInAndIdNot(
	            idHabitacion, 
	            EstadoRegistro.ACTIVO, 
	            List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO),
	            idReservacionActual
	    );
		
		if (tieneOtrasReservacionoesActivas) {
			throw new EntidadRelacionadaExeception("La habitación tiene otras reservaciones pendientes o en curso.");
		}
	}
	
	private void validarRestriccionesEnCurso(Reserva actual, ReservaRequest request) {
	    if (!actual.getFechaEntrada().isEqual(request.fechaEntrada())) {
	        throw new IllegalArgumentException("En estado EN_CURSO no se puede modificar la fecha de entrada.");
	    }
	    if (!actual.getIdHabitacion().equals(request.idHabitacion())) {
	        throw new IllegalArgumentException("En estado EN_CURSO no se puede cambiar de habitación.");
	    }
	}
}
