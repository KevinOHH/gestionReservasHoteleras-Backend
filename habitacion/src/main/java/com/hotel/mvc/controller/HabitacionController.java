package com.hotel.mvc.controller;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.service.HabitacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController extends
        CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }
    
    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerPorHabitacionId(@PathVariable Long id) {
        return ResponseEntity.ok(((HabitacionService) service).findByHabitacionId(id));
    }
    
    @PutMapping("/{idHabitacion}/disponibilidad/{idDisponibilidad}")
    public ResponseEntity<HabitacionResponse> actualizarDisponibilidadHabitacion(
    		@PathVariable Long idHabitacion, 
	        @PathVariable Long idDisponibilidad,
	        @RequestParam(required = false) Long idReservaActual // Parámetro opcional
	) {
    	return ResponseEntity.ok(service.actualizarDisponibilidadHabitacion(idHabitacion, idDisponibilidad, idReservaActual));
    }
    
    

    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<HabitacionResponse> actualizarEstado(
            @PathVariable Long id,
            @PathVariable EstadoHabitacion estado) {

        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}