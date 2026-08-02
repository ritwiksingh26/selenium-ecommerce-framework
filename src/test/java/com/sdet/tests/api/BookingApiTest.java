package com.sdet.tests.api;

import com.sdet.api.models.Booking;
import com.sdet.api.models.BookingDates;
import com.sdet.api.models.BookingResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.print.Book;

public class BookingApiTest extends ApiBaseTest{

    private static int createdBookingId;

    // TC-API-03: Create booking and validate schema
    @Test(description = "TC-API-03: Create booking and validate schema", priority = 1)
    public void testCreateBooking(){
        Booking booking = buildDefaultBooking();
        BookingResponse response = bookingClient.createBooking(booking);

        createdBookingId = response.getBookingid();

        Assert.assertTrue(createdBookingId > 0, "Booking ID should be a positive integer");
        Assert.assertEquals(response.getBooking().getFirstname(), "Ritwik", "First name should match");
        Assert.assertEquals(response.getBooking().getLastname(), "Singh", "Last name should match");
    }

    // TC-API-04: Get booking by ID
    @Test(description = "TC-API-04: Get booking should return correct data", priority = 2,
            dependsOnMethods = "testCreateBooking")
    public void testGetBookingById(){
        Response response = bookingClient.getBooking(createdBookingId);

        response.then().body(JsonSchemaValidator
                .matchesJsonSchemaInClasspath("schemas/booking-schema.json"));

        Assert.assertEquals(response.jsonPath().getString("firstname"), "Ritwik");
        Assert.assertEquals(response.jsonPath().getString("lastname"), "Singh");
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), 250);
        Assert.assertEquals(response.jsonPath().getBoolean("depositpaid"), true);
    }

    // TC-API-05: Full update booking
    @Test(description = "TC-API-05: PUT should fully update booking", priority = 3,
            dependsOnMethods = "testCreateBooking")
    public void testFullyUpdateBooking(){
        Booking updateBooking = new Booking("Updated", "User", 500, false,
                new BookingDates("2025-02-01", "2025-02-10"), "Lunch");
        Response response = bookingClient.updateBooking(createdBookingId,updateBooking);

        Assert.assertEquals(response.jsonPath().getString("firstname"), "Updated");
        Assert.assertEquals(response.jsonPath().getString("lastname"), "User");
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), 500);

    }

    // TC-API-06: Partial update booking
    @Test(description = "TC-API-06: PATCH should partailly update booking", priority = 4,
            dependsOnMethods = "testCreateBooking")
    public void testPartailUpdateBooking(){
        String partialBody = "{ \"firstname\":\"Patched\" }";
        Response response = bookingClient.partialUpdateBooking(createdBookingId, partialBody);

        Assert.assertEquals(response.jsonPath().getString("firstname"), "Patched");
    }

    // TC-API-07: Delete booking
    @Test(description = "TC-API-07: DELETE should remove booking", priority = 5, dependsOnMethods = "testCreateBooking")
    public void testDeleteBooking(){
        Response response = bookingClient.deleteBooking(createdBookingId);
        Assert.assertEquals(response.statusCode(), 201, "DELETE should return 201");
    }

    // TC-API-08: Filter bookings by name
    @Test(description = "TC-API-08: Filter bookings by first and last name", priority = 1)
    public void testGetBookingsByName(){
        bookingClient.createBooking(buildDefaultBooking());

        Response response = bookingClient.getBookingByName("Ritwik", "Singh");
        int count = response.jsonPath().getList("$").size();

        Assert.assertTrue(count > 0, "should find atleast one booking for Ritwik Singh");
    }

}
