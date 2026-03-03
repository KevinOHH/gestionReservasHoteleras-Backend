package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="reservas")
public interface ReservaClient  {
	
	@GetMapping("/id-huesped/{idHuesped}/reservas-activas")
	Void huespedTieneConsultasConfirmadasEnCurso(@PathVariable Long idHuesped);

	@GetMapping("reservacion/id-huesped/{idHuesped}")
	Void eliminarReservacionSiHuespedEliminado(@PathVariable Long idHuesped);
	
	@GetMapping("/id-habitacion/{idhabitacion}/reservas-activas")
	Boolean habitacionTieneReservacionesActivas(@PathVariable Long idhabitacion);

	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas")
	void habitacionTieneReservasConfirmadasoEnCurso(
			@PathVariable Long idHabitacion);
	
	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas-sin-actual/{idReservaActual}")
	void habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(
			@PathVariable Long idHabitacion,
			@PathVariable Long idReservaActual);
}
