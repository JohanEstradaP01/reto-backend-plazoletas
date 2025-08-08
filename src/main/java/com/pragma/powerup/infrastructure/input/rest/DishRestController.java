package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DishAvailabilityDto;
import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.service.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping
    public void updateDish(@RequestBody DishRequestDto dishRequestDto, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        String identification = jwtProvider.getClaims(token).get("identification", String.class);
        dishHandler.updateDish(dishRequestDto, identification);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/availability")
    public void changeAvailability(@RequestBody DishAvailabilityDto dishAvailabilityDto, HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization").substring(7);
        String identification = jwtProvider.getClaims(bearerToken).get("identification", String.class);
        dishHandler.changeAvailability(dishAvailabilityDto, identification);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/restaurant")
    public ResponseEntity<List<DishResponseDto>> getDishesByRestaurant(@RequestParam(value = "restaurantId") Long restaurantId,
                                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                       @RequestParam(value = "offset", required = false) Integer offset,
                                                                       @RequestParam(value = "category", required = false) Long category ){
        if (offset == null) {offset = 0;}
        if (pageSize == null) {pageSize = 10;}
        return ResponseEntity.ok(dishHandler.getDishesByRestaurant(restaurantId, pageSize, offset, category));
    }


}
