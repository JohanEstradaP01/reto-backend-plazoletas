package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishAvailabilityDto;
import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.application.mapper.IDishResponseMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.ICategoryUseCase;
import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DIshHandler implements IDishHandler {

    private final IDishUseCase dishUseCase;
    private final IDishRequestMapper dishRequestMapper;
    private final ICategoryUseCase categoryUseCase;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void createDish(DishRequestDto dishRequestDto, String identification) {
        Dish mappedDish = dishRequestMapper.toDish(dishRequestDto);
        Long categoryId = dishRequestDto.getCategoryId();
        Category category = categoryUseCase.getCategory(categoryId);

        mappedDish.setCategory(category);

        dishUseCase.createDish(mappedDish, identification);
    }

    @Override
    public void updateDish(DishRequestDto dishRequestDto, String identification) {
        dishUseCase.updateDish(dishRequestMapper.toDish(dishRequestDto), identification);
    }

    @Override
    public void changeAvailability(DishAvailabilityDto dishAvailabilityDto, String identification) {
        long id = dishAvailabilityDto.getId();
        boolean availability = dishAvailabilityDto.isAvailability();

        dishUseCase.changeAvailability(id, availability, identification);
    }

    @Override
    public List<DishResponseDto> getDishesByRestaurant(Long restaurantId, Integer pageSize, Integer offSet, Long category) {
        List<Dish> dishes;
        if (category == null){
            dishes = dishUseCase.getDishesByRestaurant(restaurantId, pageSize, offSet);
        }
        else {
            dishes = dishUseCase.getDishesByRestaurant(restaurantId, pageSize, offSet, category);
        }
        List<DishResponseDto> dishResponse = new ArrayList<>();
        for (Dish dish : dishes) {
            dishResponse.add(dishResponseMapper.toDishResponse(dish));
        }
        return dishResponse;
    }


}
