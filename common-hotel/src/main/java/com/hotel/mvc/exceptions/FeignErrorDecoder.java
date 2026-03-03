package com.hotel.mvc.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 409) {
            return new NegocioException("No se puede eliminar el huésped porque tiene reservas CONFIRMADAS o EN_CURSO");
        }
        return defaultDecoder.decode(methodKey, response);
    }
}