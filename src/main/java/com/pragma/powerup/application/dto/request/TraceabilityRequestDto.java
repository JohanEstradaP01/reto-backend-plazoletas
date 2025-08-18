package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraceabilityRequestDto {

    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime date;
    private State previousState;
    private State newState;
    private Long employeeId;
    private String employeeEmail;

}
