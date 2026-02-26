package com.hotel.mvc.service;

import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.enums.EstadoHabitacion;
import com.hotel.mvc.services.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {

	HabitacionResponse findByHabitacionId(Long id);
	
	HabitacionResponse actualizarDisponibilidadHabitacion(Long idHabitacion, Long idDisponibilidad, Long idReservaActual);
	
	
	HabitacionResponse cambiarEstado(Long id, EstadoHabitacion estado);
}