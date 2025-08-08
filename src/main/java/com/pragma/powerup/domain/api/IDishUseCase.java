package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Dish;

import java.util.List;

public interface IDishUseCase {

    public void createDish(Dish dish, String identification);

    public void updateDish(Dish dish, String identification);

    public void changeAvailability(Long id, boolean availability, String identification);

    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet);

    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet, Long category);

}
