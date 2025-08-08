package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Dish;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IDishPersistencePort {

    public void saveDish(Dish dish);

    public Dish getDish(Long id);

    public List<Dish> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offset);

    public List<Dish> getDishesByRestaurant(Long restaurantId, Long category, Integer pageSize, Integer offset);

}
