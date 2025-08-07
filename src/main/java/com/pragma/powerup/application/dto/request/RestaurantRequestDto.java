package com.pragma.powerup.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data

public class RestaurantRequestDto {

    private Long id;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = ".*\\D+.*")
    private String name;

    @NotEmpty
    @NotBlank
    private String address;

    @NotNull
    private Long ownerId;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{1,12}$")
    private String phoneNumber;

    @NotEmpty
    @NotBlank
    private String logoUrl;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String nit;

}
