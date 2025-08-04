package com.pragma.powerup.infrastructure.out.jpa.client.implementacion;

import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IClientUserPort;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserClient implements IClientUserPort {

    private final IFeignClient feignClient;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public User getUser(Long id) {
        UserResponseDto user = feignClient.consultUser(id);
        return userResponseMapper.toUser(user);
    }

}
