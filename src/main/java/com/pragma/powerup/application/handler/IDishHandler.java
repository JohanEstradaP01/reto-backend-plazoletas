package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.DishAvailabilityDto;
import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;

import java.util.List;


public interface IDishHandler {

    public void createDish(DishRequestDto dishRequestDto, String identification);

    public void updateDish(DishRequestDto dishRequestDto, String identification);

    public void changeAvailability(DishAvailabilityDto dishAvailabilityDto, String identification);

    public List<DishResponseDto> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet, Long category);

}
