package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.ICategoryUseCase;
import com.pragma.powerup.domain.api.IDishUseCase;
import com.pragma.powerup.domain.api.IObjectServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.CategoryUseCase;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.domain.usecase.ObjectUseCase;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.ObjectJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignClient;
import com.pragma.powerup.infrastructure.out.jpa.client.implementacion.UserClient;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IObjectRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
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
    private final IFeignClient feignClient;
    private final IDishEntityMapper entityMapper;
    private final IDishRepository dishRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryRepository categoryRepository;

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

}