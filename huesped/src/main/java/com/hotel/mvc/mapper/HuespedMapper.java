package com.hotel.mvc.mapper;

import com.hotel.mvc.dto.HuespedRequest;
import com.hotel.mvc.dto.HuespedResponse;
import com.hotel.mvc.entities.Huesped;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.Nacionalidad;
import com.hotel.mvc.enums.TipoDocumento;

import org.springframework.stereotype.Component;

@Component
public class HuespedMapper {

    public Huesped toEntity(HuespedRequest request) {
        return Huesped.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .telefono(request.telefono())
                .documento(TipoDocumento.fromNombre(request.tipoDocumento()))
                .nacionalidad(Nacionalidad.fromNombre(request.nacionalidad()))
                .estado(EstadoRegistro.ACTIVO)
                .build();
    }

    public HuespedResponse toResponse(Huesped huesped) {
        return new HuespedResponse(
                huesped.getId(),
                huesped.getNombre(), 
                huesped.getApellido(), 
                huesped.getEmail(),
                huesped.getTelefono(),
                huesped.getDocumento().name(),
                huesped.getNacionalidad().name(),
                huesped.getEstado().name()
        );
    }

    public void updateEntity(Huesped huesped, HuespedRequest request) {
        huesped.setNombre(request.nombre());
        huesped.setApellido(request.apellido());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setDocumento(TipoDocumento.fromNombre(request.tipoDocumento()));
        huesped.setNacionalidad(Nacionalidad.fromNombre(request.nacionalidad()));
    }
}