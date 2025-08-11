package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface IOrderHandler {

    public void createOrder(OrderRequestDto orderRequestDto);

}
