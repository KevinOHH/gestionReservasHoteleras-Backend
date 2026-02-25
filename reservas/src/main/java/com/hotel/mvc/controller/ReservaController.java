package com.hotel.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.ReservaRequest;
import com.hotel.mvc.dto.ReservaResponse;
import com.hotel.mvc.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService> {

	public ReservaController (ReservaService service) {
		super(service);
	}
	
	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas")
	public ResponseEntity<Void> habitacionTieneReservasConfirmadasoEnCurso(
            @PathVariable Long idHabitacion) {
		service.habitacionTieneReservasConfirmadasoEnCurso(idHabitacion);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/id-habitacion/{idHabitacion}/reservas-activas-sin-actual/{idReservaActual}")
    public ResponseEntity<Void> habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(
            @PathVariable Long idHabitacion,
            @PathVariable Long idReservaActual) {
		service.habitacionTieneReservasConfirmadasoEnCursoSinReservaActual(idHabitacion, idReservaActual);
    	return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{idReserva}/estado/{idEstado}")
	public ResponseEntity<Void> cambiarEstadoReserva(@PathVariable Long idReserva, @PathVariable Long idEstado) {
		service.cambiarEstadoReserva(idReserva, idEstado);
		return ResponseEntity.ok().build();
	}
}
