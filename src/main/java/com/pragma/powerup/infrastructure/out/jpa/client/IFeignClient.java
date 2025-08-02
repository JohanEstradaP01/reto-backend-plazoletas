package com.pragma.powerup.infrastructure.out.jpa.client;

import com.pragma.powerup.application.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userClient", url = "http://localhost:8080/api/v1/user")
public interface IFeignClient {

    @GetMapping("/{id}")
    public UserResponseDto consultUser(Long id);


}
