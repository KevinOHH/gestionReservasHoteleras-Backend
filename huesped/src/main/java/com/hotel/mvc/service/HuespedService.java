package com.hotel.mvc.service;

import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {

    HuespedResponse findByHuespedId(Long id);
}