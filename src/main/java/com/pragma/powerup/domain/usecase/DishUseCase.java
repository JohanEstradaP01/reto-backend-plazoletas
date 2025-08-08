package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;

import java.util.List;

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
            throw new RuntimeException();
        }

        if(!restaurantValidationPort.existRestaurantById(dish.getRestaurantId())){
            throw new RuntimeException();
        }
        System.out.println(identification+ " "+ dish.getRestaurantId());
        if (!restaurantValidationPort.isOwner(identification, dish.getRestaurantId())) {
            throw new RuntimeException();
        }

        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Dish dish, String identification) {

        Long restaurantId = dishPersistencePort.getDish(dish.getId()).getRestaurantId();
        if (!restaurantValidationPort.isOwner(identification, restaurantId)) {
            throw new RuntimeException();
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
        if (!restaurantValidationPort.isOwner(identification, restaurantId)) {
            throw new RuntimeException();
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

}
