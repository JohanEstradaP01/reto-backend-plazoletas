package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Category;

public interface ICategoryUseCase {

    public Category getCategory(Long id);

}
