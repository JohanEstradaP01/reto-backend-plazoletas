package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.domain.model.Restaurant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void createRestaurant(RestaurantRequestDto restaurantRequestDto) throws RestaurantAlreadyExist {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        restaurantServicePort.createRestaurant(restaurant);
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantServicePort.getAllRestaurants();
        ArrayList<RestaurantResponseDto> response = new ArrayList<>();
        for (Restaurant restaurant : restaurants){
            response.add(restaurantResponseMapper.toRestaurantResponseDto(restaurant));
        }
        return response;
    }

}
