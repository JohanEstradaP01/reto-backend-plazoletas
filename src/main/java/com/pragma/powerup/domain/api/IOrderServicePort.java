package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;

import java.util.List;

public interface IOrderServicePort {

    public void createOrder(Order order);

    public List<Order> getOrdersByState(Integer pageSize, Integer offset, String state, Long restaurantId);

}
