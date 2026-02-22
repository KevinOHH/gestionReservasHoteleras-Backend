package com.hotel.mvc.exceptions;

import com.hotel.mvc.dto.ErrorResponse;
import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.exceptions.EntidadRelacionadaExeception;
import com.hotel.mvc.exceptions.InvalidOperationException;
import com.hotel.mvc.exceptions.ResourceNotFoundException;

import feign.FeignException;
import feign.RetryableException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        String mensaje = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        mensaje.isEmpty() ? "Error de validación" : mensaje));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String mensaje = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación en los datos enviados");

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensaje));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }


    @ExceptionHandler(EntidadRelacionadaExeception.class)
    public ResponseEntity<ErrorResponse> handleEntidadRelacionadaException(EntidadRelacionadaExeception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error interno del servidor. Por favor, contacte al administrador."));
    }
     
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleGenericFeignException(FeignException e) {
        //log.error("Error en la comunicación Feign: " + e.getMessage());

        int status = e.status() > 0 ? e.status() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = switch (status) {
            case 400 -> "Solicitud incorrecta al servicio remoto.";
            case 401 -> "No autorizado para acceder al servicio remoto.";
            case 403 -> "Acceso prohibido al servicio remoto.";
            case 404 -> "Recurso no encontrado en el servicio remoto.";
            case 409 -> "Conflicto: el recurso tiene dependencias activas.";
            case 503 -> "Servicio remoto no disponible.";
            default -> "Error al comunicarse con el servicio remoto.";
        };
        ErrorResponse response = new ErrorResponse(status, message);

        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> handleRetryable(RetryableException e) {
    	//log.error("Servicio remoto no disponible o no responde: " + e.getMessage());
    	ErrorResponse response = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
    			"Servicio remoto no disponible o no responde");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode().value(), e.getReason())); 
    }

}