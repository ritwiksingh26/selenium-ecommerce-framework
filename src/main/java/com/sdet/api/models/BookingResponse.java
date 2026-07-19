package com.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponse {

    @JsonProperty("bookingid")
    private int bookingid;

    @JsonProperty("booking")
    private int booking;

    public int getBookingid(){
        return bookingid;
    }

    public void setBookingid(int bookingid){
        this.bookingid = bookingid;
    }

    public int getBooking(){
        return booking;
    }

    public void setBooking(int booking){
        this.booking = booking;
    }
}
