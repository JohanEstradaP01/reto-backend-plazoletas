package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.TraceabilityModel;

public interface ITraceabilityPort {

    public void createTraceability(TraceabilityModel traceabilityModel);

}
