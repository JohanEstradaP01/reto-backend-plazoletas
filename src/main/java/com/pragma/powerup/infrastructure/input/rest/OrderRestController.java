package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.service.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;
    private final JwtProvider jwtProvider;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto){
        orderHandler.createOrder(orderRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrdersByState(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "state") String state,
            HttpServletRequest httpServletRequest
    ) {
        if (offset == null) {offset = 0;}
        if (pageSize == null) {pageSize = 10;}
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String email = jwtProvider.getClaims(token).getSubject();
        System.out.println(email);
        return ResponseEntity.ok(orderHandler.getOrdersByState(pageSize, offset, state));
    }

}
