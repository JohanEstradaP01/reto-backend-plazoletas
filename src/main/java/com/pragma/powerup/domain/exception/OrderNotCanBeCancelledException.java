package com.pragma.powerup.domain.exception;

public class OrderNotCanBeCancelledException extends RuntimeException {
    public OrderNotCanBeCancelledException() {
        super("Lo sentimos, tu pedido ya esta en preparación y no puede cancelarse");
    }
}
