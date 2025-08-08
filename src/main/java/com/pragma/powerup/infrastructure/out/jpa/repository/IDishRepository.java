package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    public List<DishEntity> findAllByRestaurantId(Long restaurantId, Pageable pageable);

    public List<DishEntity> findAllByRestaurantIdAndCategoryId(Long restaurantId, Long category, Pageable pageable);

}
