package com.transferz.controller.dto;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FlightDTOTest {
    private FlightDTO flightDTO;
    private Pair<Airport, Airport> airports;

    @BeforeEach
    public void setup() {
        flightDTO = new FlightDTO();
        flightDTO.setCode("code1");
        flightDTO.setOriginAirportCode("origin1");
        flightDTO.setDestinationAirportCode("destination1");
        flightDTO.setDepartureTime(LocalDateTime.now().toString());
        flightDTO.setArrivalTime(LocalDateTime.now().toString());

        Airport origin = new Airport("code", "name", "country");
        Airport destiny = new Airport("code", "name", "country");

        airports = Pair.of(origin, destiny);
    }

    @Test
    public void toEntity_ReturnsCorrectFlightEntity() {
        Flight flight = flightDTO.toEntity(airports);

        assertEquals(flightDTO.getCode(), flight.getCode());
        assertEquals(airports.getFirst(), flight.getOriginAirport());
        assertEquals(airports.getSecond(), flight.getDestinationAirport());
        assertEquals(LocalDateTime.parse(flightDTO.getDepartureTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME), flight.getDepartureTime());
        assertEquals(LocalDateTime.parse(flightDTO.getArrivalTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME), flight.getArrivalTime());
    }
}