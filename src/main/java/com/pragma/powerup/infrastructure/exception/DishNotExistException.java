package com.pragma.powerup.infrastructure.exception;

public class DishNotExistException extends RuntimeException {
    public DishNotExistException() {
        super("El plato indicado no existe");
    }
}
