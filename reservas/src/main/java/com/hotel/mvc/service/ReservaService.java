package com.hotel.mvc.service;

import com.hotel.mvc.dto.ReservaRequest;
import com.hotel.mvc.dto.ReservaResponse;
import com.hotel.mvc.services.CrudService;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse> {

	void cambiarEstadoReserva(Long idReserva, Long idEstado);
	
	void habitacionTieneReservasConfirmadasoEnCurso(Long idHabitacion);
	void habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(Long idHabitacion, Long idReservacionActual);
	
}
