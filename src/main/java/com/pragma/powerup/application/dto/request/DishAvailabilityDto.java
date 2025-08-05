package com.pragma.powerup.application.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishAvailabilityDto {

    private long id;
    private boolean availability;

}
