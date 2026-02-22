package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.entities.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper {

    public Huesped toEntity(HuespedRequest request) {
        return Huesped.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .telefono(request.telefono())
                .documento(request.documento())
                .nacionalidad(request.nacionalidad())
                .estado("ACTIVO")
                .build();
    }

    public HuespedResponse toResponse(Huesped huesped) {
        return new HuespedResponse(
                huesped.getId(),
                huesped.getNombre(),
                huesped.getApellido(),
                huesped.getEmail(),
                huesped.getTelefono(),
                huesped.getDocumento(),
                huesped.getNacionalidad(),
                huesped.getEstado()
        );
    }

    public void updateEntity(Huesped huesped, HuespedRequest request) {
        huesped.setNombre(request.nombre());
        huesped.setApellido(request.apellido());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setDocumento(request.documento());
        huesped.setNacionalidad(request.nacionalidad());
    }
}