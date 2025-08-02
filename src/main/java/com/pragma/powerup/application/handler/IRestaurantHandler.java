package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;

public interface IRestaurantHandler {

    public void createRestaurant(RestaurantRequestDto restaurantRequestDto) throws RestaurantAlreadyExist;

}
