package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {

    public void createOrder(Order order);

    public List<Order> getPendingOrdersByCustomer(Long customerId);

    public List <Order> getOrdersByRestaurantAndState(Integer pageSize, Integer offset, String state, Long restaurantId);

    public Order getOrder(Long orderId);

}
