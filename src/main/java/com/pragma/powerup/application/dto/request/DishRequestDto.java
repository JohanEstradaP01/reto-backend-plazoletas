package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class DishRequestDto {

    private Long id;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    private Long categoryId;

    @NotEmpty
    @NotBlank
    private String description;

    @NotEmpty
    private float price;

    @NotEmpty
    private Long restaurantId;

    @NotEmpty
    @NotBlank
    private String urlImage;

    private boolean isActive;

}
