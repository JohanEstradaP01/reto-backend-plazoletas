package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.domain.model.Restaurant;

public interface IRestaurantServicePort {

    public void createRestaurant(Restaurant restaurant) throws RestaurantAlreadyExist;

}
