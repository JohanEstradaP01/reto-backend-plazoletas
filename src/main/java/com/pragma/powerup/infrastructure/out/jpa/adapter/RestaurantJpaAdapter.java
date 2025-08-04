package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.SimpleTimeZone;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort, IRestaurantValidationPort {

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(entity);
    }

    @Override
    public Restaurant findRestaurantByNit(String nit) {
        Optional<RestaurantEntity> entity = restaurantRepository.findByNit(nit);
        return entity.map(restaurantEntityMapper::toRestaurant).orElse(null);
    }

    @Override
    public boolean existRestaurantById(Long id) {
        Optional<RestaurantEntity> entity = restaurantRepository.findById(id);
        return entity.isPresent();
    }
}
