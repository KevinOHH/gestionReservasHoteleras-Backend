package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservas")//, url = "http://localhost:8083/api/reservas"
public interface ReservaClient {

	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas")
	void habitacionTieneReservasConfirmadasoEnCurso(
			@PathVariable Long idHabitacion);
	
	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas-sin-actual/{idReservaActual}")
	void habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(
			@PathVariable Long idHabitacion,
			@PathVariable Long idReservaActual);
	
}
