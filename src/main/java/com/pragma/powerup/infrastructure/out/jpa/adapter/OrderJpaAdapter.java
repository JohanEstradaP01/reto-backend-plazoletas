package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.model.State;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.exception.OrderNotExistException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final IUserServicePort userServicePort;

    private Order mapOrderEntityToOrder(OrderEntity orderEntity) {
        Order order = orderEntityMapper.toOrder(orderEntity);
        order.setCustomer(userServicePort.getUserById(orderEntity.getCustomerId()));
        List<OrderDish> orderDishes = orderEntity.getDishes().stream()
                .map(orderDishEntity -> {
                    OrderDish orderDish = orderDishEntityMapper.toOrder(orderDishEntity);
                    orderDish.setDish(dishEntityMapper.toDish(orderDishEntity.getDish()));
                    return orderDish;
                })
                .collect(Collectors.toList());
        order.setRestaurant(restaurantEntityMapper.toRestaurant(orderEntity.getRestaurant()));
        order.setDishes(orderDishes);
        return order;
    }

    @Override
    @Transactional
    public void createOrder(Order order) {
        OrderEntity savedOrderEntity = saveOrder(order);
        saveOrderDishes(order.getDishes(), savedOrderEntity);
    }

    private OrderEntity saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toOrderEntity(order);
        orderEntity.setRestaurant(restaurantEntityMapper.toEntity(order.getRestaurant()));
        orderEntity.setCustomerId(order.getCustomer().getId());
        return orderRepository.save(orderEntity);
    }

    private List<OrderDishEntity> saveOrderDishes(List<OrderDish> dishes, OrderEntity savedOrderEntity) {
        List<OrderDishEntity> orderDishEntityList = new ArrayList<>();
        for (OrderDish orderDish : dishes) {
            OrderDishEntity savedDish = createAndSaveOrderDish(orderDish, savedOrderEntity);
            orderDishEntityList.add(savedDish);
        }
        return orderDishEntityList;
    }

    private OrderDishEntity createAndSaveOrderDish(OrderDish orderDish, OrderEntity savedOrderEntity) {
        DishEntity dishEntity = dishRepository.findById(orderDish.getDish().getId())
                .orElseThrow(() -> new EntityNotFoundException("Dish not found"));
        OrderDishEntity orderDishEntity = new OrderDishEntity();
        orderDishEntity.setId(new OrderDishId(savedOrderEntity.getId(), dishEntity.getId()));
        orderDishEntity.setQuantity(orderDish.getQuantity());
        orderDishEntity.setOrder(savedOrderEntity);
        orderDishEntity.setDish(dishEntity);
        return orderDishRepository.save(orderDishEntity);
    }


    @Override
    public List<Order> getPendingOrdersByCustomer(Long customerId) {
        List<OrderEntity> orders = orderRepository.findByCustomerIdAndState(customerId, State.PENDING);
        List<Order> ordersModel = new ArrayList<>();
        for (OrderEntity order : orders) {
            ordersModel.add(orderEntityMapper.toOrder(order));
        }
        return ordersModel;
    }

    @Override
    public List<Order> getOrdersByRestaurantAndState(Integer pageSize, Integer offset, String state, Long restaurantId) {
        PageRequest pageRequest = PageRequest.of(offset, pageSize);
        List<Order> orders = new ArrayList<>();
        List<OrderEntity> orderEntities = orderRepository.findByRestaurantIdAndState(restaurantId, State.valueOf(state), pageRequest);
        for (OrderEntity order : orderEntities) {
            Order orderModel = mapOrderEntityToOrder(order);
            orders.add(orderModel);
        }
        return orders;
    }

    @Override
    public Order getOrder(Long orderId) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()){
            throw new OrderNotExistException();
        }
        return mapOrderEntityToOrder(orderOptional.get());
    }


}
