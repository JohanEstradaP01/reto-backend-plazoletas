package com.pragma.powerup.domain.spi;

public interface IRestaurantValidationPort {

    public boolean existRestaurantById(Long id);

    public boolean isOwner(String identification, Long restaurantId);

}
