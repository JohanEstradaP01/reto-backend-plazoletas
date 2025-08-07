package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data

public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phoneNumber;
    private String logoUrl;
    private String nit;

}
