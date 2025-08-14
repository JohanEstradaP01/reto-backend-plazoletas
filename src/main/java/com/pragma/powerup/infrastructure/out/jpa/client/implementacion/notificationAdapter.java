package com.pragma.powerup.infrastructure.out.jpa.client.implementacion;

import com.pragma.powerup.domain.model.NotificationModel;
import com.pragma.powerup.domain.spi.INotificationPort;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignNotificationClient;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.NotificationRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class notificationAdapter implements INotificationPort {

    private final IFeignNotificationClient feignNotificationClient;
    private final NotificationRequestMapper entityMapper;

    public void notify(NotificationModel notificationModel) {
        feignNotificationClient.notify(entityMapper.toNotificationDto(notificationModel));
    }

}
