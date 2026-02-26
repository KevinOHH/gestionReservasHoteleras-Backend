package com.hotel.mvc.controller;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.service.HabitacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController extends
        CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<HabitacionResponse> actualizarEstado(
            @PathVariable Long id,
            @PathVariable EstadoHabitacion estado) {

        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}