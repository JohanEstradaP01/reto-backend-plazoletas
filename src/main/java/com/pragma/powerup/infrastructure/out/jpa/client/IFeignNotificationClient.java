package com.pragma.powerup.infrastructure.out.jpa.client;

import com.pragma.powerup.application.dto.request.NotificationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationClient", url = "http://localhost:8082/api/v1/notification")
public interface IFeignNotificationClient {

    @PostMapping
    public void notify(@RequestBody NotificationRequestDto notificationRequestDto);

}
