package com.pragma.powerup.domain.exception;

public class RestaurantDoesNotContainDishException extends RuntimeException {
    public RestaurantDoesNotContainDishException() {
        super("El plato no existe en el restaurante");
    }
}
