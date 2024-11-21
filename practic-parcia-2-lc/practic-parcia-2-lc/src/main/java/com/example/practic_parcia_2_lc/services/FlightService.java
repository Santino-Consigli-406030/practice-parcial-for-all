package com.example.practic_parcia_2_lc.services;

import com.example.practic_parcia_2_lc.client.HttpClient;
import com.example.practic_parcia_2_lc.modelsResponse.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final HttpClient httpClient;

    private String urlBase = "http://localhost:3000";

    public List<FlightResponse> getFlights() {
        ResponseEntity<List<FlightResponse>> response =
                httpClient.get(urlBase + "/flights",
                        new ParameterizedTypeReference<List<FlightResponse>>() {
                });
        if(response.getBody()!=null){
            return response.getBody();
        }
        else{
            throw new RuntimeException("No se encontraron vuelos");
        }
    }

    public FlightResponse getFlightById(String flightId) {
        ResponseEntity<FlightResponse> response =
                httpClient.get(urlBase + "/flights/"+flightId,
                        new ParameterizedTypeReference<FlightResponse>() {
                        });
        if(response.getBody()!=null){
            return response.getBody();
        }
        else{
            throw new RuntimeException("No se encontro el vuelo");
        }
    }
}
