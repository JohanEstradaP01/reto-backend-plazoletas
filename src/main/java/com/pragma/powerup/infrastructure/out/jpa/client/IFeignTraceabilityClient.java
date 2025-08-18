package com.pragma.powerup.infrastructure.out.jpa.client;

import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "traceability", url = "http://localhost:8083/api/v1/traceability")
public interface IFeignTraceabilityClient {

    @PostMapping
    public void createTraceability(@RequestBody TraceabilityRequestDto traceabilityRequestDto);

}
