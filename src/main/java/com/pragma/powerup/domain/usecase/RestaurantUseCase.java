package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IClientUserPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IClientUserPort clientUserPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IClientUserPort clientUserPort){
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.clientUserPort = clientUserPort;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) throws RestaurantAlreadyExist {

        Long ownerId = restaurant.getOwnerId();
        User owner = clientUserPort.getUser(ownerId);

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException(); //cambiar error
        }
        if (restaurantPersistencePort.findRestaurantByNit(restaurant.getNit()) != null) {
            throw new RestaurantAlreadyExist();
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }


}
