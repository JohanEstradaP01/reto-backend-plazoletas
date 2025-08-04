package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;

public interface IClientUserPort {

    public User getUser(Long id);

}
