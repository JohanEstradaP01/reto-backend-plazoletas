package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DishRequestDto {

    private Long id;

    @NotBlank
    private String name;

    private Long categoryId;

    @NotBlank
    private String description;

    private float price;

    private Long restaurantId;

    @NotBlank
    private String urlImage;

    private boolean isActive;

}
