package com.example.practic_parcia_2_lc.controllers;

import com.example.practic_parcia_2_lc.modelsResponse.FlightResponse;
import com.example.practic_parcia_2_lc.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/flights")
@RequiredArgsConstructor
public class FlightController {


    private final FlightService flightService;
    @GetMapping()
    public List<FlightResponse> getFlights() {
        return flightService.getFlights();
    }
    @GetMapping("/{flight_id}")
    public FlightResponse getFlightById(@PathVariable("flight_id") String flightId) {
        return flightService.getFlightById(flightId);
    }
}
