package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;

public class DishUseCase implements IDishUseCase {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantValidationPort restaurantValidationPort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantValidationPort restaurantValidationPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantValidationPort = restaurantValidationPort;
    }

    @Override
    public void createDish(Dish dish) {
        if(dish.getPrice() < 0){
            throw new RuntimeException();
        }
        if(!restaurantValidationPort.existRestaurantById(dish.getRestaurantId())){
            throw new RuntimeException();
        }
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Dish dish) {
        Dish dishToUpdate = dishPersistencePort.getDish(dish.getId());
        dishToUpdate.setPrice(dish.getPrice());
        dishToUpdate.setDescription(dish.getDescription());
        dishPersistencePort.saveDish(dishToUpdate);
    }

    @Override
    public void changeAvailability(Long id, boolean availability) {
        Dish dish = dishPersistencePort.getDish(id);
        dish.setActive(availability);
        dishPersistencePort.saveDish(dish);
    }

}
