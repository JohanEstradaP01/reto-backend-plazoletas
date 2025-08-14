package com.pragma.powerup.domain.exception;

public class PriceIsNegativeException extends RuntimeException {
    public PriceIsNegativeException() {
        super("El precio no debe ser negativo");
    }
}
