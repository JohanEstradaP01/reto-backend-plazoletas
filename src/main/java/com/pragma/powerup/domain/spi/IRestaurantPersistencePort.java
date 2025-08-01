package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    public void saveRestaurant(Restaurant restaurant);

}
