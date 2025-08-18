package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.domain.model.TraceabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityMapper {

    public TraceabilityRequestDto toRequestDto(TraceabilityModel traceabilityModel);

}
