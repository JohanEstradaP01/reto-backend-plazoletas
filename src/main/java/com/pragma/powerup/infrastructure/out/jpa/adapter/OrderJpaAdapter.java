package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.*;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort{

    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderRepository orderRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IDishRepository dishRepository;

    @Override
    @Transactional
    public void createOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toOrderEntity(order);
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(order.getRestaurant());
        orderEntity.setRestaurant(restaurantEntity);
        orderEntity.setCustomerId(order.getCustomer().getId());
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        List<OrderDishEntity> orderDishEntityList = new ArrayList<>();
        for (OrderDish orderDish : order.getDishes()) {
            DishEntity dishEntity = dishRepository.findById(orderDish.getDish().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Dish not found"));
            OrderDishId orderDishId = new OrderDishId();
            orderDishId.setOrderId(savedOrderEntity.getId());
            orderDishId.setDishId(dishEntity.getId());
            OrderDishEntity orderDishEntity = new OrderDishEntity();
            orderDishEntity.setId(orderDishId);
            orderDishEntity.setQuantity(orderDish.getQuantity());
            orderDishEntity.setOrder(savedOrderEntity);
            orderDishEntity.setDish(dishEntity);
            OrderDishEntity savedOrderDish = orderDishRepository.save(orderDishEntity);
            orderDishEntityList.add(savedOrderDish);
            savedOrderEntity.setDishes(orderDishEntityList);

        }
    }

}
