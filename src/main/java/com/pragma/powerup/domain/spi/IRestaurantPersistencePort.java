package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {

    public void saveRestaurant(Restaurant restaurant);

    public Restaurant findRestaurantByNit(String nit);

    public List<Restaurant> getAllRestaurant();

}
