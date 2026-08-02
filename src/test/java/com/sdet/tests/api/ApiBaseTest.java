package com.sdet.tests.api;

import com.sdet.api.clients.BookingApiClient;
import com.sdet.api.models.Booking;
import com.sdet.api.models.BookingDates;
import org.testng.annotations.BeforeClass;

public class ApiBaseTest {

    protected BookingApiClient bookingClient;

    @BeforeClass
    public void setUpApi(){
        bookingClient = new BookingApiClient();
        bookingClient.generateToken();
    }

    // Reuseable test data builder
    protected Booking buildDefaultBooking(){
        return new Booking("Ritwik", "Singh", 250, true,
                new BookingDates("2025-01-01", "2025-01-07"), "Breakfast");
    }
}
