package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.domain.model.State;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    public List<OrderEntity> findByCustomerIdAndState(Long customerId, State state);

    public List<OrderEntity> findByRestaurantIdAndState(Long restaurantId, State state, Pageable pageable);

}
