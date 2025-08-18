package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DeliverOrderRequestDto;
import com.pragma.powerup.application.dto.request.OrderCancelDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.ReadyOrderRequestDto;
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
            @RequestHeader("Authorization") String authorization
    ) {
        if (offset == null) {offset = 0;}
        if (pageSize == null) {pageSize = 10;}
        String token = authorization.substring(7);
        String email = jwtProvider.getClaims(token).getSubject();
        System.out.println(email);
        return ResponseEntity.ok(orderHandler.getOrdersByState(pageSize, offset, state));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/assign")
    public ResponseEntity<Void> assignOrder(@RequestHeader("Authorization") String authorization, @RequestBody ReadyOrderRequestDto order){
        Long orderId = order.getOrderId();
        String token = authorization.substring(7);
        Long employeeId = ((Integer) jwtProvider.getClaims(token).get("id")).longValue();
        System.out.println(employeeId);
        orderHandler.assignOrder(orderId, employeeId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/ready")
    public ResponseEntity<Void> readyOrder(@RequestHeader("Authorization") String authorization, @RequestBody ReadyOrderRequestDto orderRequestDto) {
        orderHandler.orderReady(orderRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/deliver")
    public ResponseEntity<Boolean> deliverOrder(@RequestBody DeliverOrderRequestDto deliverOrderRequestDto) {
        return ResponseEntity.ok(orderHandler.deliverOrder(deliverOrderRequestDto));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(@RequestBody OrderCancelDto orderCancel, @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long id = (Long) jwtProvider.getClaims(token).get("id");
        orderHandler.cancelOrder(orderCancel, id);
        return ResponseEntity.ok().build();
    }

}
