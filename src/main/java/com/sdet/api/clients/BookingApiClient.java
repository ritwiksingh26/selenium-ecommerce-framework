package com.sdet.api.clients;

import com.sdet.api.config.ApiConfig;
import com.sdet.api.models.AuthRequest;
import com.sdet.api.models.Booking;
import com.sdet.api.models.BookingResponse;
import com.sdet.config.ConfigReader;
import com.sdet.utils.LogUtil;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class BookingApiClient {

    private static final Logger log = LogUtil.getLogger(BookingApiClient.class);
    private String authToken;

    public BookingApiClient(){
        ApiConfig.init();
    }

    // Auth

    public String generateToken(){
        AuthRequest authRequest = new AuthRequest(
                ConfigReader.get("apiUsername"),
                ConfigReader.get("apiPassword")
        );

        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .body(authRequest)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().response();

        authToken = response.jsonPath().getString("token");
        log.info("Auth token generated successfully");
        return authToken;
    }

    // Create

    public BookingResponse createBooking(Booking booking){
        log.info("Creating booking for: {} {}", booking.getFirstname(), booking.getLastname());

        return given()
                .spec(ApiConfig.getRequestSpec())
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    // Read

    public Response getBooking(int bookingId){
        log.info("Fetching booking ID: {}", bookingId);

        return given()
                .spec(ApiConfig.getRequestSpec())
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getAllBookings(){
        return given()
                .spec(ApiConfig.getRequestSpec())
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getBookingByName(String firstname, String lastname){
        return given()
                .spec(ApiConfig.getRequestSpec())
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .extract().response();
    }

    // Update
    public Response updateBooking(int bookingId, Booking booking){
        log.info("Updating booking ID: {}", bookingId);

        return given()
                .spec(ApiConfig.getRequestSpec())
                .header("Cookie", "token=" + authToken)
                .body(booking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response partialUpdateBooking(int bookingId, String body){
        log.info("Partial update on booking ID: {}", bookingId);

        return given()
                .spec(ApiConfig.getRequestSpec())
                .header("Cookie", "token="+authToken)
                .body(body)
                .when()
                .patch("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().response();
    }

    // Delete

    public Response deleteBooking(int bookingId){
        log.info("Deleting booking ID: {}", bookingId);

        return given()
                .spec(ApiConfig.getRequestSpec())
                .header("Cookie", "token=" + authToken)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201)
                .extract().response();
    }
}
