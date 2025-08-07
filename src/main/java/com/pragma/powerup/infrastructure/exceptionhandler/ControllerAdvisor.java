package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.domain.exception.UserNotIsOwnerException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(RestaurantAlreadyExist.class)
    public ResponseEntity<String> handlerRestaurantAlreadyExist(){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("El restaurante registrado ya existe");
    }

    @ExceptionHandler(UserNotIsOwnerException.class)
    public ResponseEntity<String> userIsNotOwnerException(UserNotIsOwnerException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El usuario no es un propietario");
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> feignException(FeignException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Error con la solicitud");
    }

    
}