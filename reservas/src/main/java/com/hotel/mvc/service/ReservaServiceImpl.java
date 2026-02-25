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
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.EstadoReserva;
import com.hotel.mvc.mappers.ReservaMapper;
import com.hotel.mvc.repository.ReservaRepository;

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
    
    //private final HabitacionClient habitacionClient;
    
    @Override
    @Transactional(readOnly = true)
	public List<ReservaResponse> listar() {
		log.info("Listado de todas las reservas activas solicitado");
		return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(reserva -> reservaMapper.entityToResponse(
						reserva,
						//null,
						getHuespedResponseSinEstado(reserva.getIdHuesped()),
						null
						//getHabitacionResponseSinEstado(reserva.getIdHabitacion())
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
				null
				//getHabitacionResponseSinEstado(reserva.getIdHabitacion())
				);
	}

	@Override
	@Transactional
	public ReservaResponse registrar(ReservaRequest request) {
		log.info("Registrando nueva reservacion {}", request);
		
		//FALTAN VALIDACIONES
		validarHabitacionSinReservaActivas(request.idHabitacion());
		
		
		
		HuespedResponse huesped = getHuespedResponse(request.idHuesped());
		////HabitacionResponse habitacion = getHabitacionResponse(request.idHabitacion());
		
		Reserva reserva = reservaRepository.save(reservaMapper.requestToEntity(request));
		
		log.info("Reserva refistrada exitosamente: {}", reserva);
		return reservaMapper.entityToResponse(reserva, huesped, null);
	}

	@Override
	@Transactional
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		Reserva reserva = getReservaOrThrow(id);
		log.info("Actualizando reserva: {}", reserva.getId());
		
		//EstadoReserva estadoNuevo = EstadoReserva.fromCodigo(request.idEstadoReserva());
		
		////HuespedResponse huesped = getHuespedResponse(request.idHuesped());
		///////HabitacionResponse habitacion = getHabitacionResponse(idHabitacionNueva);
		////HabitacionResponse habitacion = getHabitacionResponse(request.idHabitacion());
		
		
		log.info("Reserva actualizada exitosamente: {}", reserva);
		//return reservaMapper.entityToResponse(reserva, huesped, habitacion);
		return reservaMapper.entityToResponse(reserva, null, null);
	}

	@Override
	public void eliminar(Long id) {
		Reserva reserva = getReservaOrThrow(id);
		
		log.info("Eliminando reserva con id: {}", id);
		
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
	
	private void validarHabitacionSinReservaActivas(Long idHuesped) {
		boolean tieneReservaActiva = reservaRepository.existsByIdHuespedAndEstadoRegistroAndEstadoReservaIn(
				idHuesped, 
				EstadoRegistro.ACTIVO, 
				List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO));
		
		if (tieneReservaActiva) {
			throw new IllegalStateException("El huesped ya tiene una reservación activa CONFIRMADA o EN_CURSO");
		}
	}
	/*
	private HabitacionResponse getHabitacionResponse(Long idHabitacion) {
		return habitacionClient.obtenerHabitacionPorId(idHabitacion);
	}
	
	private HabitacionResponse getHabitacionResponseSinEstado(Long idHabitacion) {
		return habitacionClient.obtenerHabitacionPorIdSinEstado(idHabitacion);
	}
	*/
}
