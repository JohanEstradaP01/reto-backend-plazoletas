package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Dish;

public interface IDishPersistencePort {

    public void saveDish(Dish dish);

    public Dish getDish(Long id);

}
