package com.example.practic_parcia_2_lc.models;


import lombok.Data;

import java.util.UUID;

@Data
public class Ticket {
    private UUID id;
    private double price;
    private String seatClass;
    private String status;
}