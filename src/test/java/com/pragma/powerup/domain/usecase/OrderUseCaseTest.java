package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.OrderNotCanBeCancelledException;
import com.pragma.powerup.domain.exception.RestaurantDoesNotContainDishException;
import com.pragma.powerup.domain.exception.UserHasMoreOrdersException;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IDishRestaurantConsultPort;
import com.pragma.powerup.domain.spi.INotificationPort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.ITraceabilityPort;
import com.pragma.powerup.infrastructure.exception.OrderNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IDishRestaurantConsultPort dishRestaurantConsultPort;
    @Mock
    private INotificationPort notificationPort;
    @Mock
    private ITraceabilityPort traceabilityPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private User customer;
    private User employee;
    private Restaurant restaurant;
    private Dish dish;
    private Order order;


    @Test
    void createOrder_Success() {
        when(dishRestaurantConsultPort.getDishesByRestaurant(10L, 1000, 0))
                .thenReturn(List.of(dish));
        when(orderPersistencePort.getPendingOrdersByCustomer(1L))
                .thenReturn(Collections.emptyList())
                .thenReturn(List.of(order));

        orderUseCase.createOrder(order);

        verify(orderPersistencePort).createOrder(order);
        verify(traceabilityPort).createTraceability(any());
    }

    @Test
    void createOrder_RestaurantDoesNotContainDish_ShouldThrow() {
        when(dishRestaurantConsultPort.getDishesByRestaurant(10L, 1000, 0))
                .thenReturn(Collections.emptyList());

        assertThrows(RestaurantDoesNotContainDishException.class,
                () -> orderUseCase.createOrder(order));

        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void createOrder_UserHasMoreOrders_ShouldThrow() {
        when(dishRestaurantConsultPort.getDishesByRestaurant(10L, 1000, 0))
                .thenReturn(List.of(dish));
        when(orderPersistencePort.getPendingOrdersByCustomer(1L))
                .thenReturn(List.of(order));

        assertThrows(UserHasMoreOrdersException.class,
                () -> orderUseCase.createOrder(order));

        verify(orderPersistencePort, never()).createOrder(any());
    }


    @Test
    void deliverOrder_WrongCode_ShouldReturnFalse() {
        order.setState(State.READY);
        when(orderPersistencePort.getOrder(200L)).thenReturn(order);

        boolean result = orderUseCase.deliverOrder(200L, 9999);

        assertFalse(result);
        verify(orderPersistencePort, never()).createOrder(any());
    }


    @Test
    void cancelOrder_NotPending_ShouldThrow() {
        order.setState(State.READY);
        when(orderPersistencePort.getOrder(200L)).thenReturn(order);

        assertThrows(OrderNotCanBeCancelledException.class,
                () -> orderUseCase.cancelOrder(200L, 1L));

        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void cancelOrder_WrongUser_ShouldThrow() {
        order.setState(State.PENDING);
        order.setCustomer(User.builder().id(99L).build());
        when(orderPersistencePort.getOrder(200L)).thenReturn(order);

        assertThrows(OrderNotExistException.class,
                () -> orderUseCase.cancelOrder(200L, 1L));

        verify(orderPersistencePort, never()).createOrder(any());
    }
}
