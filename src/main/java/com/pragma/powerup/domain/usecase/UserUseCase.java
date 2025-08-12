package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IClientUserPort;

public class UserUseCase implements IUserServicePort {

    private final IClientUserPort clientUserPort;

    public UserUseCase(IClientUserPort clientUserPort) {
        this.clientUserPort = clientUserPort;
    }

    @Override
    public User getUser() {
        return clientUserPort.getUserByEmail();
    }

    @Override
    public User getUserById(Long id) {
        return clientUserPort.getUser(id);
    }

}
