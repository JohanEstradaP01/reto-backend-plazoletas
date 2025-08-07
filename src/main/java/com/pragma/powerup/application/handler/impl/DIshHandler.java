package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishAvailabilityDto;
import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.ICategoryUseCase;
import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DIshHandler implements IDishHandler {

    private final IDishUseCase dishUseCase;
    private final IDishRequestMapper dishRequestMapper;
    private final ICategoryUseCase categoryUseCase;

    @Override
    public void createDish(DishRequestDto dishRequestDto, String identification) {
        Dish mappedDish = dishRequestMapper.toDish(dishRequestDto);
        Long categoryId = dishRequestDto.getCategoryId();
        Category category = categoryUseCase.getCategory(categoryId);

        mappedDish.setCategory(category);

        dishUseCase.createDish(mappedDish, identification);
    }

    @Override
    public void updateDish(DishRequestDto dishRequestDto) {
        dishUseCase.updateDish(dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void changeAvailability(DishAvailabilityDto dishAvailabilityDto) {
        long id = dishAvailabilityDto.getId();
        boolean availability = dishAvailabilityDto.isAvailability();

        dishUseCase.changeAvailability(id, availability);
    }

}
