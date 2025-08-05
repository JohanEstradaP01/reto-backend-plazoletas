package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Dish;

public interface IDishUseCase {

    public void createDish(Dish dish);

    public void updateDish(Dish dish);

    public void changeAvailability(Long id, boolean availability);

}
