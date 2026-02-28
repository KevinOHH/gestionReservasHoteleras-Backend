package com.hotel.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.mvc.dto.huesped.HuespedResponse;

@FeignClient(name = "huesped", url = "http://localhost:8082")
public interface HuespedClient {

	@GetMapping("/{id}")
	HuespedResponse obtenerHuespedPorId(@PathVariable Long id);
	
	@GetMapping("/api/huespedes/id-huesped/{id}")
	HuespedResponse obtenerHuespedPorIdSinEstado(@PathVariable Long id);
	
}
