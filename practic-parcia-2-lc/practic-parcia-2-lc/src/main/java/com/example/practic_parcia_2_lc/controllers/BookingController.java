package com.example.practic_parcia_2_lc.controllers;

import com.example.practic_parcia_2_lc.models.BookingRequest;
import com.example.practic_parcia_2_lc.models.BookingResponse;
import com.example.practic_parcia_2_lc.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;


    @PostMapping("/book")
    public BookingResponse postBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.postBooking(bookingRequest);
    }
}
