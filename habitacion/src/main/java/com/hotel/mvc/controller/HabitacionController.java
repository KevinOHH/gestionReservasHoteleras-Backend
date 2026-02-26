package com.hotel.mvc.controller;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.service.HabitacionService;
import com.hotel.mvc.service.HabitacionServiceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    private final HabitacionServiceImpl habitacionServiceImpl;
    

    public HabitacionController(HabitacionService habitacionservice, HabitacionServiceImpl habitacionServiceImpl) {
        super(habitacionservice);
        this.habitacionServiceImpl = habitacionServiceImpl;
    }
    
    @PatchMapping("/{id}/estado/{idEstado}")
    public HabitacionResponse actualizarEstado(
    		@PathVariable Long id,
    		@PathVariable EstadoRegistro idEstadoRegistro) {
    	return
    			habitacionServiceImpl.cambiarEstado(id, idEstadoRegistro);
    }
}