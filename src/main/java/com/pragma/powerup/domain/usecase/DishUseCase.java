package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.exception.PriceIsNegativeException;
import com.pragma.powerup.domain.exception.RestaurantNotExistException;
import com.pragma.powerup.domain.exception.UserNotIsOwnerException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;

import java.util.List;
import java.util.Objects;

public class DishUseCase implements IDishUseCase {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantValidationPort restaurantValidationPort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantValidationPort restaurantValidationPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantValidationPort = restaurantValidationPort;
    }

    @Override
    public void createDish(Dish dish, String identification) {
        if(dish.getPrice() <= 0){
            throw new PriceIsNegativeException();
        }

        if(!restaurantValidationPort.existRestaurantById(dish.getRestaurantId())){
            throw new RestaurantNotExistException();
        }

        if (!isOwner(Long.getLong(identification), dish.getRestaurantId())) {
            throw new UserNotIsOwnerException();
        }

        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Dish dish, String identification) {

        Long restaurantId = dishPersistencePort.getDish(dish.getId()).getRestaurantId();
        if (!isOwner(Long.getLong(identification), restaurantId)) {
            throw new UserNotIsOwnerException();
        }

        Dish dishToUpdate = dishPersistencePort.getDish(dish.getId());
        dishToUpdate.setPrice(dish.getPrice());
        dishToUpdate.setDescription(dish.getDescription());
        dishPersistencePort.saveDish(dishToUpdate);
    }

    @Override
    public void changeAvailability(Long id, boolean availability, String identification) {
        Dish dish = dishPersistencePort.getDish(id);
        dish.setActive(availability);

        Long restaurantId = dishPersistencePort.getDish(dish.getId()).getRestaurantId();
        if (!isOwner(Long.getLong(identification), restaurantId)) {
            throw new UserNotIsOwnerException();
        }
        
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet) {
        return dishPersistencePort.getDishesByRestaurant(restaurantId, pageSize, offSet);
    }

    @Override
    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet, Long category) {
        return dishPersistencePort.getDishesByRestaurant(restaurantId, category,  pageSize, offSet);
    }

    @Override
    public Dish getDish(Long id) {
        return dishPersistencePort.getDish(id);
    }

    private boolean isOwner(Long restaurantId, Long ownerIdentification){
        Restaurant restaurant = restaurantValidationPort.getRestaurant(restaurantId);
        return Objects.equals(restaurant.getOwnerId(), ownerIdentification);
    }

}
