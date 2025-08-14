package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.NotificationModel;

public interface INotificationPort {

    public void notify(NotificationModel notificationModel);

}
