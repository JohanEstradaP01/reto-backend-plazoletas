package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.domain.exception.RestaurantAlreadyExist;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void createRestaurant(@Valid @RequestBody RestaurantRequestDto restaurant) throws RestaurantAlreadyExist {
        restaurantHandler.createRestaurant(restaurant);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "offset", required = false) Integer offset
    ){
        if (pageSize == null) {
            pageSize = 10;
        }
        if (offset == null) {
            offset = 0;
        }
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(pageSize, offset));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/owner/{restaurantId}")
    public ResponseEntity<Long> getRestaurantOwner(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantHandler.getRestaurantOwner(restaurantId));
    }

}
