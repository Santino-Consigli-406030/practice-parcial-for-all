package com.example.practic_parcia_2_lc.models;

import lombok.Data;

@Data
public class BookingRequest {
    private int flightId;
    private int seatId;
    private String passenger;
}
