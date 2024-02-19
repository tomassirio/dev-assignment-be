package com.transferz.controller;

import com.transferz.controller.dto.PassengerDTO;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.exception.FlightNotFoundException;
import com.transferz.service.FlightService;
import com.transferz.service.PassengerService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final FlightService flightService;

    public PassengerController(PassengerService passengerService, FlightService flightService) {
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @PostMapping
    @Timed(value = "passenger.createPassenger", description = "Time taken to create a passenger")
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody PassengerDTO passengerDTO){
        log.info("Received request to create passenger: {}", passengerDTO);

        Flight flight = flightService.getFlight(passengerDTO.getFlightCode())
                .orElseThrow(() -> {
                    log.error("Flight not found with code: {}", passengerDTO.getFlightCode());
                    return new FlightNotFoundException(passengerDTO.getFlightCode());
                });

        Passenger createdPassenger = passengerService.addPassenger(passengerDTO.toEntity(flight));
        log.info("Created passenger with id {}", createdPassenger.getPassengerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }
}