package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;

public interface IOrderPersistencePort {

    public void createOrder(Order order);

}
