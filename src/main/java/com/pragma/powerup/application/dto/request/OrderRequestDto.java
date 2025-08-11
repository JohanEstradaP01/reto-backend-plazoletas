package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.model.State;
import com.pragma.powerup.domain.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDate date;

    @NotNull
    private State state;

    @NotNull
    private Long chefId;

    @NotNull
    private Long restaurant;

    @NotEmpty
    private List<OrderDishRequestDto> orderDish;

}
