package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data

public class RestaurantRequestDto {

    private Long id;

    @NotBlank
    @Pattern(regexp = ".*\\D+.*")
    private String name;

    @NotBlank
    private String address;

    private Long ownerId;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{1,12}$")
    private String phoneNumber;

    @NotBlank
    private String logoUrl;

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String nit;

}
