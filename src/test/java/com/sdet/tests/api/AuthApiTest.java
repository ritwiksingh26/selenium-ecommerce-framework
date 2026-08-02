package com.sdet.tests.api;

import com.sdet.api.config.ApiConfig;
import com.sdet.api.models.AuthRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthApiTest extends ApiBaseTest{

    // TC-API-01: Valid credentials return token
    @Test(description = "TC-API-01: Valid credentials should return auth token")
    public void testValidationAuthReturnsToken(){
        String token = bookingClient.generateToken();
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
    }

    // TC-API-02: Invalid credentials return error message
    @Test(description = "TC-API-02: Invalid credentials should return Bad credentials message")
    public void testInvalidAuthReturnsError(){
        ApiConfig.init();
        AuthRequest invalidAuth = new AuthRequest("wrong_username", "wrong_password");

        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .body(invalidAuth)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().response();

        String reason = response.jsonPath().getString("reason");
        Assert.assertEquals(reason, "Bad credentials",
                "Invalid auth should return Bad credentials message");
    }
}
