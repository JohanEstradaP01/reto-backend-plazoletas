package com.pragma.powerup.domain.exception;

public class UserNotIsOwnerException extends RuntimeException {
    public UserNotIsOwnerException() {
        super("El usuario seleccionado no tiene el rol de propietario");
    }
}
