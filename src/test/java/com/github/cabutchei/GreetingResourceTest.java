package com.github.cabutchei;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    private static final String FORECAST_PAYLOAD = """
            {"latitude":52.52,"longitude":13.41,"hourly":{"temperature_2m":[12.3,13.1]}}
            """;

    @Test
    void testForecastEndpoint() {
        given()
          .when().get("/forecast")
          .then()
             .statusCode(200)
             .contentType(containsString("application/json"))
             .body(is(FORECAST_PAYLOAD));
    }
}
