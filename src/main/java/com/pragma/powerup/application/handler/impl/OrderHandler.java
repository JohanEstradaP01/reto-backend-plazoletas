package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IDishUseCase dishUseCase;
    private final IRestaurantServicePort restaurantServicePort;
    private final IUserServicePort userServicePort;


    @Override
    public void createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        List<OrderDish> dishes = new ArrayList<>();
        for (OrderDishRequestDto orderDish : orderRequestDto.getOrderDish()) {
            OrderDish orderModel = new OrderDish();
            orderModel.setDish(dishUseCase.getDish(orderDish.getDish()));
            orderModel.setQuantity(orderDish.getQuantity());
            dishes.add(orderModel);
        }
        Dish dish = dishes.get(0).getDish();
        Restaurant restaurant = restaurantServicePort.getRestaurant(dish.getRestaurantId());
        User user = userServicePort.getUser();
        System.out.println(user.getName());
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDishes(dishes);
        order.setState(State.PENDING);
        orderServicePort.createOrder(order);
    }

}
