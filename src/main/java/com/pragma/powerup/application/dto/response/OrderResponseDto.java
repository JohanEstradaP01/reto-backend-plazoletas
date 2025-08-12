package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDate date;

    @NotNull
    private State state;

    @NotNull
    private Long chefId;

    @NotEmpty
    private List<OrderDishResponseDto> orderDish;

}
