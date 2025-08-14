package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.application.dto.request.NotificationRequestDto;
import com.pragma.powerup.domain.model.NotificationModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationRequestMapper {

    public NotificationRequestDto toNotificationDto(NotificationModel notificationModel);

}
