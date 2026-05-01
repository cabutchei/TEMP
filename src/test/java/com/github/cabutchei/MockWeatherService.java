package com.github.cabutchei;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@Alternative
@Priority(1)
@ApplicationScoped
public class MockWeatherService implements WeatherService {

    @Override
    public String getForecastPayload() {
        return """
                {"latitude":52.52,"longitude":13.41,"hourly":{"temperature_2m":[12.3,13.1]}}
                """;
    }
}
