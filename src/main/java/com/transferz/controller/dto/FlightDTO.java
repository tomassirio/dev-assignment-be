package com.transferz.controller.dto;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

@Data
public class FlightDTO {
    private String code;
    private String originAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    public Flight toEntity(Pair<Airport, Airport> airports) {
        return new Flight(this.code, airports.getFirst(), airports.getSecond(), this.departureTime, this.arrivalTime);
    }

}
