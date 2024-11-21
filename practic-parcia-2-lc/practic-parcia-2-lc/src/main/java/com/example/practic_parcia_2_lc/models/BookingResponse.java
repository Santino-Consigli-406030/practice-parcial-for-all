package com.example.practic_parcia_2_lc.models;

import com.example.practic_parcia_2_lc.modelsResponse.FlightResponse;
import com.example.practic_parcia_2_lc.modelsResponse.SeatResponse;
import lombok.Data;

@Data
public class BookingResponse {
    private int id;
    private FlightBooking flight;
    private SeatResponse seat;
    private String passenger;
    private Ticket ticket;
    private String status;
}
