package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.DishRequestDto;


public interface IDishHandler {

    public void createDish(DishRequestDto dishRequestDto);

    public void updateDish(DishRequestDto dishRequestDto);

}
