package com.example.practic_parcia_2_lc.modelsResponse;

import lombok.Data;

import java.util.List;

@Data
public class AirPlaneResponse {
    private String model;
    private List<SeatResponse> seats;
}
