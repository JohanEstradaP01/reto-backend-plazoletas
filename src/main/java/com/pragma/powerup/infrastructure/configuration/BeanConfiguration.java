package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.out.jpa.adapter.*;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignUserClient;
import com.pragma.powerup.infrastructure.out.jpa.client.implementacion.UserClient;
import com.pragma.powerup.infrastructure.out.jpa.mapper.*;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IUserResponseMapper userResponseMapper;
    private final IFeignUserClient feignClient;
    private final IDishEntityMapper entityMapper;
    private final IDishRepository dishRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final INotificationPort notificationPort;
    private final ITraceabilityPort traceabilityPort;

    @Bean
    public IObjectPersistencePort objectPersistencePort() {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort() {
        return new ObjectUseCase(objectPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IClientUserPort clientUserPort(){
        return new UserClient(feignClient, userResponseMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(), clientUserPort() );
    }

    @Bean
    IDishPersistencePort dishPersistencePort(){
        return new DishJpaAdapter(entityMapper, dishRepository);
    }

    @Bean
    IDishRestaurantConsultPort dishRestaurantConsultPort(){
        return new DishJpaAdapter(entityMapper, dishRepository);
    }

    @Bean
    public IRestaurantValidationPort restaurantValidationPort(){
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IDishUseCase dishServicePort(){
        return new DishUseCase(dishPersistencePort(), restaurantValidationPort());
    }

    @Bean
    public ICategoryUseCase categoryUseCase(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(clientUserPort());
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), dishRestaurantConsultPort(), notificationPort, traceabilityPort, userServicePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderEntityMapper, restaurantEntityMapper, orderDishEntityMapper, orderRepository, entityMapper, orderDishRepository, dishRepository, userServicePort());
    }

}