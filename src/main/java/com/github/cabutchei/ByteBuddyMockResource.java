package com.github.cabutchei;

import java.net.http.HttpClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/forecast/mock")
public class ByteBuddyMockResource {

    private static final String MOCKED_PAYLOAD = "\"mocked\"";

    @Inject
    OpenMeteoService openMeteoService;

    @Inject
    ByteBuddyMockHttpClientFactory httpClientFactory;

    @Inject
    ScopedFieldOverride scopedFieldOverride;

    @GET
    public Response forecast() {
        HttpClient mockClient = httpClientFactory.createJsonMockClient(MOCKED_PAYLOAD);
        String payload = scopedFieldOverride.withFieldValue(
                openMeteoService,
                OpenMeteoService.class,
                "httpClient",
                mockClient,
                openMeteoService::getForecastPayload);

        return Response.ok(payload, MediaType.APPLICATION_JSON).build();
    }
}
