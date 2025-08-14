package com.pragma.powerup.domain.exception;

public class UserHasMoreOrdersException extends RuntimeException {
    public UserHasMoreOrdersException() {
        super("El usuario tiene mas ordenes en estado pendiente");
    }
}
