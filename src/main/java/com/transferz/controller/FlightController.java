package com.transferz.controller;

import com.transferz.controller.dto.FlightDTO;
import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.exception.AirportNotFoundException;
import com.transferz.service.AirportService;
import com.transferz.service.FlightService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
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
    @Timed(value = "flight.createFlight", description = "Time taken to create a flight")
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        log.info("Received request to create flight: {}", flightDTO);

        Airport origin = airportService.getAirport(flightDTO.getOriginAirportCode())
                .orElseThrow(() -> {
                    log.error("Airport not found with code: {}", flightDTO.getOriginAirportCode());
                    return new AirportNotFoundException(flightDTO.getOriginAirportCode());
                });

        Airport destination = airportService.getAirport(flightDTO.getDestinationAirportCode())
                .orElseThrow(() -> {
                    log.error("Airport not found with code: {}", flightDTO.getDestinationAirportCode());
                    return new AirportNotFoundException(flightDTO.getDestinationAirportCode());
                });

        Flight createdFlight = flightService.addFlight(flightDTO.toEntity(Pair.of(origin, destination)));
        log.info("Created flight with id {}", createdFlight.getFlightId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }
}