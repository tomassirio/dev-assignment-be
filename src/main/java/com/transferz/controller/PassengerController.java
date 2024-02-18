package com.transferz.controller;

import com.transferz.controller.dto.PassengerDTO;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.exception.FlightNotFoundException;
import com.transferz.service.FlightService;
import com.transferz.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerDTO passengerDTO){
        Flight flight = flightService.getFlight(passengerDTO.getFlightCode())
                .orElseThrow(() -> new FlightNotFoundException(passengerDTO.getFlightCode()));

        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.addPassenger(passengerDTO.toEntity(flight)));
    }
}