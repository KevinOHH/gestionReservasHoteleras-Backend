package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.mvc.dto.habitacion.HabitacionResponse;

@FeignClient(name = "habitacion")//, url = "http://localhost:8081"
public interface HabitacionClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	//@GetMapping("/api/habitaciones/id-habitacion/{id}")
	@GetMapping("/id-habitacion/{id}")
	HabitacionResponse obtenerHabitacionPorIdSinEstado(@PathVariable Long id);
	
	//@PutMapping("/api/habitaciones/{idHabitacion}/disponibilidad/{idDisponibilidad}")
    @PutMapping("/{idHabitacion}/disponibilidad/{idDisponibilidad}")
    void actualizarDisponibilidad(@PathVariable Long idHabitacion, 
    		@PathVariable Long idDisponibilidad,
    		@RequestParam(value = "idReservaActual", required = false) Long idReservaActual);
}
