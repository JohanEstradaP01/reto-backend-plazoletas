package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderRequestMapper {

    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "customer", ignore = true)
    public Order toOrder(OrderRequestDto orderRequestDto);

}
