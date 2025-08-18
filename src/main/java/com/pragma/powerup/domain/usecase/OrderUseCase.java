package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.OrderNotCanBeCancelledException;
import com.pragma.powerup.domain.exception.RestaurantDoesNotContainDishException;
import com.pragma.powerup.domain.exception.UserHasMoreOrdersException;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IDishRestaurantConsultPort;
import com.pragma.powerup.domain.spi.INotificationPort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.ITraceabilityPort;
import com.pragma.powerup.infrastructure.exception.OrderNotExistException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IDishRestaurantConsultPort dishRestaurantConsultPort;
    private final INotificationPort notificationPort;
    private final ITraceabilityPort traceabilityPort;
    private final IUserServicePort userServicePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishRestaurantConsultPort dishRestaurantConsultPort, INotificationPort notificationPort, ITraceabilityPort traceabilityPort, IUserServicePort userServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishRestaurantConsultPort = dishRestaurantConsultPort;
        this.notificationPort = notificationPort;
        this.traceabilityPort = traceabilityPort;
        this.userServicePort = userServicePort;
    }

    private void createTraceability(User customer, User employee, Order order, State previousState){
        TraceabilityModel traceability = new TraceabilityModel();
        traceability.setCustomerEmail(customer.getEmail());
        traceability.setDate(LocalDateTime.now());
        traceability.setOrderId(order.getId());
        traceability.setCustomerId(customer.getId());
        traceability.setEmployeeId(employee.getId());
        traceability.setEmployeeEmail(employee.getEmail());
        traceability.setPreviousState(previousState);
        traceability.setNewState(order.getState());
        traceabilityPort.createTraceability(traceability);
    }

    private void createTraceability(User customer, Order order, State previousState){
        TraceabilityModel traceability = new TraceabilityModel();
        traceability.setCustomerEmail(customer.getEmail());
        traceability.setDate(LocalDateTime.now());
        traceability.setOrderId(order.getId());
        traceability.setCustomerId(customer.getId());
        traceability.setPreviousState(previousState);
        traceability.setNewState(order.getState());
        traceabilityPort.createTraceability(traceability);
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
            throw new UserHasMoreOrdersException();
        }
        orderPersistencePort.createOrder(order);
        Order newOrder = orderPersistencePort.getPendingOrdersByCustomer(order.getCustomer().getId()).get(0);
        createTraceability(order.getCustomer(), newOrder, null);
    }

    @Override
    public List<Order> getOrdersByState(Integer pageSize, Integer offset, String state, Long restaurantId) {
        return orderPersistencePort.getOrdersByRestaurantAndState(pageSize, offset, state, restaurantId);
    }

    @Override
    public void assignOrder(Long orderId, Long employeeId) {
        Order order = orderPersistencePort.getOrder(orderId);
        order.setState(State.PREPARING);
        order.setEmployeeAssigned(employeeId);
        createTraceability(order.getCustomer(), userServicePort.getUserById(order.getEmployeeAssigned()), order, State.PENDING);
        orderPersistencePort.createOrder(order);
    }

    @Override
    public void orderReady(Long orderId) {
        Order order = orderPersistencePort.getOrder(orderId);
        User customer = order.getCustomer();
        order.setState(State.READY);
        orderPersistencePort.createOrder(order);
        NotificationModel notificationModel = new NotificationModel();
        int code = Math.abs(Objects.hash(order.getId()) % 10000);
        notificationModel.setPhoneNumber(customer.getPhone());
        notificationModel.setMessage("Su orden esta lista, el código es " + String.valueOf(code));
        createTraceability(order.getCustomer(), userServicePort.getUserById(order.getEmployeeAssigned()), order, State.PREPARING);
        notificationPort.notify(notificationModel);
    }

    @Override
    public boolean deliverOrder(Long orderId, Integer code) {
        Order order = orderPersistencePort.getOrder(orderId);
        int orderCode = Math.abs(Objects.hash(order.getId()) % 10000);
        if (code == orderCode && Objects.equals(order.getState(), State.READY)) {
            order.setState(State.DELIVERED);
            orderPersistencePort.createOrder(order);
            createTraceability(order.getCustomer(), userServicePort.getUserById(order.getEmployeeAssigned()), order, State.READY);
            return true;
        }
        return false;
    }

    @Override
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderPersistencePort.getOrder(orderId);

        if (!order.getState().equals(State.PENDING)) {
            throw new OrderNotCanBeCancelledException();
        }

        if (!Objects.equals(order.getCustomer().getId(), userId)) {
            throw new OrderNotExistException();
        }

        order.setState(State.CANCELLED);
        createTraceability(order.getCustomer(), userServicePort.getUserById(order.getEmployeeAssigned()), order, State.PENDING);
        orderPersistencePort.createOrder(order);

    }

}
