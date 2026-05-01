package com.github.cabutchei;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ByteBuddyMockResourceTest {

    @Test
    void testMockedForecastEndpoint() {
        given()
          .when().get("/forecast/mock")
          .then()
             .statusCode(200)
             .contentType(containsString("application/json"))
             .body(is("\"mocked\""));
    }
}
