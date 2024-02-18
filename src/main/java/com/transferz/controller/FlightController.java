package com.transferz.controller;

import com.transferz.controller.dto.FlightDTO;
import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.exception.AirportNotFoundException;
import com.transferz.service.AirportService;
import com.transferz.service.FlightService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/flights")
public class FlightController {

    private final FlightService flightService;
    private final AirportService airportService;

    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDTO flightDTO) {
        Airport origin = airportService.getAirport(flightDTO.getOriginAirportCode())
                .orElseThrow(() -> new AirportNotFoundException(flightDTO.getOriginAirportCode()));
        Airport destination = airportService.getAirport(flightDTO.getDestinationAirportCode())
                .orElseThrow(() -> new AirportNotFoundException(flightDTO.getOriginAirportCode()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.addFlight(flightDTO
                        .toEntity(Pair.of(origin, destination))));
    }
}