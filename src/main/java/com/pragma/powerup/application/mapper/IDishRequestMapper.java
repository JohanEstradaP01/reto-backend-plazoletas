package com.pragma.powerup.application.mapper;


import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    @Mapping(target = "category", ignore = true)
    public Dish toDish(DishRequestDto dishRequestDto);

}
