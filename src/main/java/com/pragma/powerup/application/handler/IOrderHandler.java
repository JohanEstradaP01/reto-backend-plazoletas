package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderHandler {

    public void createOrder(OrderRequestDto orderRequestDto);

    public List<OrderResponseDto> getOrdersByState(Integer pageSize, Integer offset, String state);

}
