package com.hotel.mvc.controller;

import com.hotel.mvc.controllers.CommonController;
import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.service.HuespedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/huespedes")
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {

    public HuespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/id-huesped/{id}")
    public ResponseEntity<HuespedResponse> obtenerPorHuespedId(@PathVariable Long id) {
        return ResponseEntity.ok(((HuespedService) service).findByHuespedId(id));
    }
}