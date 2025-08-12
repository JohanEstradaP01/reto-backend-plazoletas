package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {

    public void createRestaurant(Restaurant restaurant) throws RestaurantAlreadyExist;

    public List<Restaurant> getAllRestaurants(Integer pageSize, Integer offset);

    public Restaurant getRestaurant(Long id);

    public Long getRestaurantOwner(Long restaurantId);

}
