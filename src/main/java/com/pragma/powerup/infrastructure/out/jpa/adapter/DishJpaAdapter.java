package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishEntityMapper entityMapper;
    private final IDishRepository dishRepository;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(entityMapper.toEntity(dish));
    }

    @Override
    public Dish getDish(Long id) {
        Optional<DishEntity> entity = dishRepository.findById(id);
        if(entity.isEmpty()){
            throw new RuntimeException();
        }
        return entityMapper.toDish(entity.get());
    }

}
