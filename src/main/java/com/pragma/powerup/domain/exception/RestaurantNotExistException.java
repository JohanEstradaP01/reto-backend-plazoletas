package com.pragma.powerup.domain.exception;

public class RestaurantNotExistException extends RuntimeException {
    public RestaurantNotExistException() {
        super("El restaurante indicado no existe");
    }
}
