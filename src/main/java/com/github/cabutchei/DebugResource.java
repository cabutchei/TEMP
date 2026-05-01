package com.github.cabutchei;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.http.HttpResponse;
import java.util.Optional;

import javax.net.ssl.SSLSession;

@Path("/debug")
public class DebugResource {

    @Inject
    OpenMeteoService openMeteoService;

    @GET
    public Response doGet() {
        try (ByteBuddyRuntimeMocks.FieldOverrideSession overrides = ByteBuddyRuntimeMocks.session()) {

            HttpResponse<String> resp = new HttpResponse<String>() {

                @Override
                public int statusCode() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'statusCode'");
                }

                @Override
                public HttpRequest request() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'request'");
                }

                @Override
                public Optional<HttpResponse<String>> previousResponse() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'previousResponse'");
                }

                @Override
                public HttpHeaders headers() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'headers'");
                }

                @Override
                public String body() {
                    return "ainnnn";
                }

                @Override
                public Optional<SSLSession> sslSession() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'sslSession'");
                }

                @Override
                public URI uri() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'uri'");
                }

                @Override
                public Version version() {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'version'");
                }
                
            };
            HttpClient mockClient = ByteBuddyRuntimeMocks.createSubclassMock(HttpClient.class, "send", (proxy, method, args) -> resp);
            
            overrides.replaceField(openMeteoService, "httpClient", mockClient);
            String payload = openMeteoService.getForecastPayload();
            return Response.ok(payload, MediaType.APPLICATION_JSON).build();
        }
    }
}