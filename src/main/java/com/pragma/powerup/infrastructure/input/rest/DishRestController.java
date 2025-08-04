package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dish")
@AllArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @PostMapping
    public void createDish(@RequestBody DishRequestDto requestDto){
        dishHandler.createDish(requestDto);
    }

    @PutMapping
    public void updateDish(@RequestBody DishRequestDto dishRequestDto){
        dishHandler.updateDish(dishRequestDto);
    }

}
