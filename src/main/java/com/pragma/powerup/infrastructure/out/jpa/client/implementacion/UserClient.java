package com.pragma.powerup.infrastructure.out.jpa.client.implementacion;

import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IClientUserPort;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignUserClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserClient implements IClientUserPort {

    private final IFeignUserClient feignClient;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public User getUser(Long id) {
        UserResponseDto user = feignClient.consultUser(id);
        return userResponseMapper.toUser(user);
    }

    @Override
    public User getUserByEmail() {
        User user;
        UserResponseDto userResponseDto = feignClient.getUser();
        user = userResponseMapper.toUser(userResponseDto);
        user.setRestaurantId(userResponseDto.getRestaurantId());
        return user;
    }


}
