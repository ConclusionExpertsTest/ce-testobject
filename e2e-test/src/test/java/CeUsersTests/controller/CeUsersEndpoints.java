package CeUsersTests.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;

import static io.restassured.RestAssured.given;
import static io.restassured.config.SSLConfig.sslConfig;

public class CeUsersEndpoints {

    private static final String baseUrl = "http://localhost:8082/api/users/";

    public static Response getAllActive() {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .when()
                .get(baseUrl);
    }

    public static Response getAllInactive() {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .when()
                .get(baseUrl + "inactive");
    }

    public static Response getById(String id) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .when()
                .get(baseUrl + id);
    }

    public static Response postNewCeUser(String requestBody) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseUrl);
    }

    public static Response putCeUser(String id, String requestBody) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseUrl + id);
    }

    public static Response deleteCeUser(String id) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .when()
                .delete(baseUrl + id);
    }

}
