package com.pragma.powerup.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDto {

    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private float price;
    private Long restaurantId;
    private String urlImage;


}
