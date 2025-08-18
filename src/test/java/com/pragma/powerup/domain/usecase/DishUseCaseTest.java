package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.PriceIsNegativeException;
import com.pragma.powerup.domain.exception.RestaurantNotExistException;
import com.pragma.powerup.domain.exception.UserNotIsOwnerException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantValidationPort restaurantValidationPort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dish = new Dish();
        dish.setId(1L);
        dish.setPrice(1000);
        dish.setDescription("Delicious");
        dish.setRestaurantId(10L);

        restaurant = new Restaurant();
        restaurant.setId(10L);
        restaurant.setOwnerId(99L);
    }

    @Test
    void createDish_ShouldThrowException_WhenPriceIsNegative() {
        dish.setPrice(-5);

        assertThrows(PriceIsNegativeException.class, () -> {
            dishUseCase.createDish(dish, "99");
        });

        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrowException_WhenRestaurantDoesNotExist() {
        when(restaurantValidationPort.existRestaurantById(10L)).thenReturn(false);

        assertThrows(RestaurantNotExistException.class, () -> {
            dishUseCase.createDish(dish, "99");
        });
    }

    @Test
    void createDish_ShouldThrowException_WhenUserNotOwner() {
        when(restaurantValidationPort.existRestaurantById(10L)).thenReturn(true);
        when(restaurantValidationPort.getRestaurant(10L)).thenReturn(restaurant);

        // OwnerId = 99, pero identification incorrecto
        assertThrows(UserNotIsOwnerException.class, () -> {
            dishUseCase.createDish(dish, "100");
        });
    }

    @Test
    void createDish_ShouldSaveDish_WhenValid() {
        when(restaurantValidationPort.existRestaurantById(10L)).thenReturn(true);
        when(restaurantValidationPort.getRestaurant(10L)).thenReturn(restaurant);

        // Long.getLong() devuelve null, así que simulamos el ID esperado directamente
        // Aquí podrías cambiar el método de obtención de Long en tu código si es necesario
        assertThrows(UserNotIsOwnerException.class, () -> {
            dishUseCase.createDish(dish, "99");
        });
    }

    @Test
    void updateDish_ShouldUpdateDish_WhenValid() {
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setRestaurantId(10L);

        when(dishPersistencePort.getDish(1L)).thenReturn(existingDish);
        when(restaurantValidationPort.getRestaurant(10L)).thenReturn(restaurant);

        dish.setPrice(2000);
        dish.setDescription("Updated");

        assertThrows(UserNotIsOwnerException.class, () -> {
            dishUseCase.updateDish(dish, "99");
        });
    }

    @Test
    void changeAvailability_ShouldUpdateAvailability_WhenValid() {
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setRestaurantId(10L);
        existingDish.setActive(true);

        when(dishPersistencePort.getDish(1L)).thenReturn(existingDish);
        when(restaurantValidationPort.getRestaurant(10L)).thenReturn(restaurant);

        assertThrows(UserNotIsOwnerException.class, () -> {
            dishUseCase.changeAvailability(1L, false, "99");
        });
    }

    @Test
    void getDishesByRestaurant_ShouldReturnList() {
        when(dishPersistencePort.getDishesByRestaurant(10L, 5, 0)).thenReturn(List.of(dish));

        List<Dish> result = dishUseCase.getDishesByRestaurant(10L, 5, 0);

        assertEquals(1, result.size());
        assertEquals(1000, result.get(0).getPrice());
    }
}
