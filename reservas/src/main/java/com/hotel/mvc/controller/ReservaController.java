package com.hotel.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.ReservaRequest;
import com.hotel.mvc.dto.ReservaResponse;
import com.hotel.mvc.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService> {

	public ReservaController (ReservaService service) {
		super(service);
	}
	
}
