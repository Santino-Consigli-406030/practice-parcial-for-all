package com.example.practic_parcia_2_lc.services;

import com.example.practic_parcia_2_lc.client.HttpClient;
import com.example.practic_parcia_2_lc.models.BookingRequest;
import com.example.practic_parcia_2_lc.models.BookingResponse;
import com.example.practic_parcia_2_lc.models.FlightBooking;
import com.example.practic_parcia_2_lc.models.Ticket;
import com.example.practic_parcia_2_lc.modelsResponse.FlightResponse;
import com.example.practic_parcia_2_lc.modelsResponse.SeatResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final FlightService flightService;

    private final HttpClient httpClient;

    private String URL ="http://localhost:3000";

    @Transactional
    public BookingResponse postBooking(BookingRequest bookingRequest) {
        FlightResponse flight = flightService.getFlightById(String.valueOf(bookingRequest.getFlightId()));
        BookingResponse bookingResponse = new BookingResponse();
        if (flight != null) {
            int capturedVersion =0;
            SeatResponse selectedSeat = null;
            Ticket ticket = new Ticket();
            for (SeatResponse seat : flight.getAirplane().getSeats()) {
                if (seat.getSeat_id() == bookingRequest.getSeatId()) {
                    capturedVersion = seat.getVersion();
                    selectedSeat = seat;
                    selectedSeat.setAvailable(Boolean.FALSE);
                    bookingResponse.setSeat(selectedSeat);
                    FlightBooking flightBooking = new FlightBooking();
                    flightBooking.setFlightNumber(flight.getFlight_number());
                    flightBooking.setOrigin(flight.getOrigin());
                    seat.setAvailable(Boolean.FALSE);
                    bookingResponse.setFlight(flightBooking);
                    bookingResponse.setPassenger(bookingRequest.getPassenger());
                    bookingResponse.setStatus("PENDING");
                    ticket.setId(UUID.randomUUID());
                    ticket.setPrice(800.10);
                    ticket.setSeatClass("ECONOMY");
                    ticket.setStatus("PENDING");
                    break;
                }
            }

            httpClient.put(URL + "/flights/" + bookingRequest.getFlightId(),
                    flight,
                    new ParameterizedTypeReference<Void>() {
                    });
            if (selectedSeat != null) {
                ResponseEntity<FlightResponse> response = httpClient.get(
                        URL + "/flights/" + bookingRequest.getFlightId(),
                        new ParameterizedTypeReference<FlightResponse>() {
                        });
                SeatResponse currentSeat = response.getBody().getAirplane().getSeats().stream()
                        .filter(seat -> seat.getSeat_id() == bookingRequest.getSeatId())
                        .findFirst()
                        .orElse(null);
                if (currentSeat != null && currentSeat.getVersion() == capturedVersion) {
                   ticket.setStatus("CONFIRM");
                   bookingResponse.setTicket(ticket);
                   bookingResponse.setStatus("CONFIRM");
                   httpClient.post(URL + "/bookings", bookingResponse,
                           new ParameterizedTypeReference<Void>() {
                           });
                } else {
                    throw new RuntimeException("El asiento ya a sido reservado. Intente nuevamente.");
                }
            } else {
                throw new RuntimeException("Asiento no encontrado.");
            }
        }
        return bookingResponse;
    }
}
