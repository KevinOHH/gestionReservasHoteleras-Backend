package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.mvc.dto.habitacion.HabitacionResponse;

@FeignClient(name = "habitacion")
public interface HabitacionClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	//@GetMapping("/id-huesped/{id}")
	@GetMapping("/id-habitacion/{id}")
	HabitacionResponse obtenerHabitacionPorIdSinEstado(@PathVariable Long id);
	
	@PutMapping("/{idHabitacion}/disponibilidad/{idDisponibilidad}")
    void actualizarDisponibilidad(@PathVariable Long idHabitacion, 
    		@PathVariable Long idDisponibilidad,
    		@RequestParam(value = "idReservaActual", required = false) Long idReservaActual);
}
