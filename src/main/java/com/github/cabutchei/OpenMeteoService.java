package com.github.cabutchei;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class OpenMeteoService implements WeatherService {

    private final URI forecastUri;
    private HttpClient httpClient;

    public OpenMeteoService(@ConfigProperty(name = "open.meteo.forecast.url") String forecastUrl) {
        this.forecastUri = URI.create(forecastUrl);
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public synchronized String getForecastPayload() {
        HttpRequest request = HttpRequest.newBuilder(forecastUri).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(
                        "Open-Meteo request failed with status " + response.statusCode(),
                        Response.Status.BAD_GATEWAY);
            }

            return response.body();
        } catch (IOException exception) {
            throw new WebApplicationException("Failed to call Open-Meteo", exception, Response.Status.BAD_GATEWAY);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException(
                    "Interrupted while calling Open-Meteo",
                    exception,
                    Response.Status.BAD_GATEWAY);
        }
    }
}
