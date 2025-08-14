package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DeliverOrderRequestDto;
import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.ReadyOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.mapper.IOrderResponseMapper;
import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.*;
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
    private final IOrderResponseMapper orderResponseMapper;


    @Override
    public void createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        List<OrderDish> dishes = new ArrayList<>();
        List<OrderDishRequestDto> requestDos = orderRequestDto.getOrderDish();
        for (OrderDishRequestDto orderDish : requestDos) {
            OrderDish orderModel = new OrderDish();
            orderModel.setDish(dishUseCase.getDish(orderDish.getDish()));
            orderModel.setQuantity(orderDish.getQuantity());
            dishes.add(orderModel);
        }
        Dish dish = dishes.get(0).getDish();
        Restaurant restaurant = restaurantServicePort.getRestaurant(dish.getRestaurantId());
        User user = userServicePort.getUser();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDishes(dishes);
        order.setState(State.PENDING);
        orderServicePort.createOrder(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByState(Integer pageSize, Integer offset, String state) {
        User employee = userServicePort.getUser();
        Long restaurantId = employee.getRestaurantId();
        System.out.println(employee.getRestaurantId());
        List<Order> ordersList = orderServicePort.getOrdersByState(pageSize, offset, state, restaurantId);
        List<OrderResponseDto> orderResponseList = new ArrayList<>();
        for (Order order : ordersList) {
            OrderResponseDto orderResponse = orderResponseMapper.toOrderResponse(order);
            List<OrderDishResponseDto> orderDishResponseList = getOrderDishResponseDtos(order);
            orderResponse.setOrderDish(orderDishResponseList);
            orderResponse.setCustomerId(order.getCustomer().getId());
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }

    @Override
    public void assignOrder(Long orderId, Long employeeId) {
        orderServicePort.assignOrder(orderId, employeeId);
    }

    @Override
    public void orderReady(ReadyOrderRequestDto orderRequestDto) {
        orderServicePort.orderReady(orderRequestDto.getOrderId());
    }

    @Override
    public boolean deliverOrder(DeliverOrderRequestDto deliverOrderRequestDto) {
        return orderServicePort.deliverOrder(deliverOrderRequestDto.getOrderId(), deliverOrderRequestDto.getCode());
    }

    private static List<OrderDishResponseDto> getOrderDishResponseDtos(Order order) {
        List<OrderDish> orderDishes = order.getDishes();
        List<OrderDishResponseDto> orderDishResponseList = new ArrayList<>();
        for(OrderDish orderDish : orderDishes) {
            OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();
            orderDishResponseDto.setDish(orderDish.getDish().getId());
            orderDishResponseDto.setQuantity(orderDish.getQuantity());
            orderDishResponseList.add(orderDishResponseDto);
        }
        return orderDishResponseList;
    }

}
