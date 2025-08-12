package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.exception.RestaurantDoesNotContainDishException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IDishRestaurantConsultPort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IDishRestaurantConsultPort dishRestaurantConsultPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishRestaurantConsultPort dishRestaurantConsultPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishRestaurantConsultPort = dishRestaurantConsultPort;
    }

    @Override
    public void createOrder(Order order) {
        List<Dish> dishes = dishRestaurantConsultPort.getDishesByRestaurant(order.getRestaurant().getId(), 1000, 0);
        List<OrderDish> orderedDishes = order.getDishes();
        List<Long> dishesId = new ArrayList<>();
        for (Dish dish : dishes){
            dishesId.add(dish.getId());
        }
        for (OrderDish dish : orderedDishes) {
            if (!dishesId.contains(dish.getDish().getId())){
                throw new RestaurantDoesNotContainDishException();
            }
        }
        if (!orderPersistencePort.getPendingOrdersByCustomer(order.getCustomer().getId()).isEmpty()) {
            throw new RuntimeException();
        }
        orderPersistencePort.createOrder(order);
    }

    @Override
    public List<Order> getOrdersByState(Integer pageSize, Integer offset, String state, Long restaurantId) {
        return orderPersistencePort.getOrdersByRestaurantAndState(pageSize, offset, state, restaurantId);
    }

}
