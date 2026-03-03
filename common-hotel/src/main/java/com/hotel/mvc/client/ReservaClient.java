package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

<<<<<<< HEAD
@FeignClient(name="reservas")
public interface ReservaClient  {
	
	@GetMapping("/id-huesped/{idHuesped}/reservas-activas")
	Void huespedTieneConsultasConfirmadasEnCurso(@PathVariable Long idHuesped);

	@GetMapping("reservacion/id-huesped/{idHuesped}")
	Void eliminarReservacionSiHuespedEliminado(@PathVariable Long idHuesped);
	
	@GetMapping("/id-habitacion/{idhabitacion}/reservas-activas")
	Boolean habitacionTieneReservacionesActivas(@PathVariable Long idhabitacion);

=======
@FeignClient(name = "reservas")//, url = "http://localhost:8083/api/reservas"
public interface ReservaClient {

	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas")
	void habitacionTieneReservasConfirmadasoEnCurso(
			@PathVariable Long idHabitacion);
	
	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas-sin-actual/{idReservaActual}")
	void habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(
			@PathVariable Long idHabitacion,
			@PathVariable Long idReservaActual);
	
>>>>>>> 7d838ab76056ace9464c77c456a43ded8354e0d6
}
