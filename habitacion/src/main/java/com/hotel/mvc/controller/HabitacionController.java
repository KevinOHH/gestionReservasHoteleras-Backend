package com.hotel.mvc.controller;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.HabitacionRequest;
import com.hotel.mvc.dto.HabitacionResponse;
import com.hotel.mvc.service.HabitacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }
}