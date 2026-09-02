package dev.scholarqa.api;

import dev.scholarqa.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Tag("api")
class JsonPlaceholderApiTest {
    @BeforeAll
    static void configureClient() {
        RestAssured.baseURI = TestConfig.apiBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        configureProxyWhenPresent();
    }

    private static void configureProxyWhenPresent() {
        String proxyUrl = System.getenv("HTTPS_PROXY");
        if (proxyUrl == null || proxyUrl.isBlank()) {
            proxyUrl = System.getenv("https_proxy");
        }
        if (proxyUrl != null && !proxyUrl.isBlank()) {
            URI proxy = URI.create(proxyUrl);
            int port = proxy.getPort() >= 0 ? proxy.getPort() : 80;
            RestAssured.proxy(proxy.getHost(), port);
        }
    }

    @Test
    void getExistingUserReturnsExpectedContract() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/users/1")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("address.city", notNullValue());
    }

    @Test
    void createPostReturnsTheSubmittedResource() {
        Map<String, Object> requestBody = Map.of(
                "title", "Quality is a team responsibility",
                "body", "Automated checks provide fast feedback.",
                "userId", 1
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(101))
                .body("title", equalTo(requestBody.get("title")))
                .body("body", equalTo(requestBody.get("body")))
                .body("userId", equalTo(requestBody.get("userId")));
    }

    @Test
    void unknownUserReturnsNotFound() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/users/999999")
        .then()
                .statusCode(404)
                .body("isEmpty()", equalTo(true));
    }
}
