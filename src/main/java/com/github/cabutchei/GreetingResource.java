package com.github.cabutchei;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/forecast")
public class GreetingResource {

    @Inject
    WeatherService weatherService;

    @GET
    public Response forecast() {
        return Response.ok(weatherService.getForecastPayload(), MediaType.APPLICATION_JSON).build();
    }
}
