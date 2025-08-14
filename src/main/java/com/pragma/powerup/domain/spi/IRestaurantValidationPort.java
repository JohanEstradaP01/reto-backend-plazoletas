package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurant;

public interface IRestaurantValidationPort {

    public boolean existRestaurantById(Long id);

    public Restaurant getRestaurant(Long restaurantId);

}
