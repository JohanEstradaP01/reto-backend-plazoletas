package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICategoryUseCase;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;

public class CategoryUseCase implements ICategoryUseCase {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category getCategory(Long id) {
        return categoryPersistencePort.getCategory(id);
    }

}
