package com.example.practic_parcia_2_lc.modelsResponse;

import lombok.Data;

@Data
public class SeatResponse {
    private int seat_id;
    private boolean available;
    private int version;
}
