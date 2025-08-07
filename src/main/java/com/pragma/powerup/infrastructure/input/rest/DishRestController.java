package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DishAvailabilityDto;
import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.service.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dish")
@AllArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;
    private final JwtProvider jwtProvider;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public void createDish(@Valid @RequestBody DishRequestDto requestDto, HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization").substring(7);
        String identification = jwtProvider.getClaims(bearerToken).get("identification", String.class);
        dishHandler.createDish(requestDto, identification);
    }

    @PutMapping
    public void updateDish(@RequestBody DishRequestDto dishRequestDto){
        dishHandler.updateDish(dishRequestDto);
    }

    @PutMapping("/availability")
    public void changeAvailability(@RequestBody DishAvailabilityDto dishAvailabilityDto){
        dishHandler.changeAvailability(dishAvailabilityDto);
    }

}
