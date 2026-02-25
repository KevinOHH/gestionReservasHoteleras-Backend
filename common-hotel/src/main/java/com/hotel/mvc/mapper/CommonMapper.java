package com.hotel.mvc.mapper;

import org.springframework.stereotype.Component;

@Component
public interface CommonMapper<RQ, RS, E> {

    RS entityToResponse(E entity);

    E requestToEntity(RQ request);
    
    E updateEntityFromRequest(RQ request, E entity);

}
