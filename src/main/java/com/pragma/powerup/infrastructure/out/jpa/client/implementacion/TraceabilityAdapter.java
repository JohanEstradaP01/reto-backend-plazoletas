package com.pragma.powerup.infrastructure.out.jpa.client.implementacion;

import com.pragma.powerup.application.mapper.ITraceabilityMapper;
import com.pragma.powerup.domain.model.TraceabilityModel;
import com.pragma.powerup.domain.spi.ITraceabilityPort;
import com.pragma.powerup.infrastructure.out.jpa.client.IFeignTraceabilityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceabilityAdapter implements ITraceabilityPort {

    private final IFeignTraceabilityClient feignTraceabilityClient;
    private final ITraceabilityMapper traceabilityMapper;

    @Override
    public void createTraceability(TraceabilityModel traceabilityModel) {
        feignTraceabilityClient.createTraceability(traceabilityMapper.toRequestDto(traceabilityModel));
    }
}
