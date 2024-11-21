package com.example.practic_parcia_2_lc.modelsResponse;

import lombok.Data;

import java.util.List;

@Data
public class FlightResponse {
    private int id;
    private String flight_number;
    private String origin;
    private String destination;
    private String airline;
    private int schedule_id;
    private List<String> crew_members;
    private AirPlaneResponse airplane;
}
