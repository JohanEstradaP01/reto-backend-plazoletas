package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantValidationPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;

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
    public List<Restaurant> getAllRestaurant(Integer pageSize, Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by("name").ascending());
        Page<RestaurantEntity> entities = restaurantRepository.findAll(pageRequest);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (RestaurantEntity restaurant : entities){
            restaurants.add(restaurantEntityMapper.toRestaurant(restaurant));
        }
        return restaurants;
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        Optional<RestaurantEntity> entity = restaurantRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException();
        }
        return restaurantEntityMapper.toRestaurant(entity.get());
    }

    @Override
    public boolean existRestaurantById(Long id) {
        Optional<RestaurantEntity> entity = restaurantRepository.findById(id);
        return entity.isPresent();
    }

    @Override
    public boolean isOwner(String identification, Long restaurantId) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.filter(restaurantEntity -> Objects.equals(String.valueOf(restaurantEntity.getOwnerId()), identification)).isPresent();
    }

}
