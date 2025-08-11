package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Dish;

import java.util.List;

public interface IDishRestaurantConsultPort {

    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offset);

}
