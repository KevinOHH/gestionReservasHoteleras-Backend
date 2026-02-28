package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.mvc.dto.habitacion.HabitacionResponse;

@FeignClient(name = "habitacion")
public interface HabitacionClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	@GetMapping("/id-huesped/{id}")
	HabitacionResponse obtenerHabitacionPorIdSinEstado(@PathVariable Long id);
	
	
}
