package com.transferz.service.impl;

import com.transferz.config.FlightProperties;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.exception.AvailableFlightNotFoundException;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.PassengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final FlightProperties flightProperties;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                FlightRepository flightRepository,
                                FlightProperties flightProperties) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.flightProperties = flightProperties;
    }

    @Override
    public Passenger addPassenger(@Valid Passenger passenger) {
        log.info("Adding passenger {}", passenger);
        Flight flight = passenger.getFlight();

        if (isFlightFull(flight.getCode())) {
            log.info("Flight with code {} is full. Trying to find an available flight", flight.getCode());
            flightRepository.findAvailableFlight(flightProperties.getMaxPassengers())
                    .ifPresentOrElse(
                            availableFlight -> {
                                passenger.setFlight(availableFlight);
                                log.info("Found available flight with code {}", availableFlight.getCode());
                            },
                            () -> {
                                log.error("No available flight found");
                                throw new AvailableFlightNotFoundException();
                            }
                    );
        }

        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info("Added passenger with id {}", savedPassenger.getPassengerId());
        return savedPassenger;
    }

    private boolean isFlightFull(String flightCode) {
        int passengerCount = passengerRepository.countPassengerByFlightCode(flightCode);
        log.info("Flight with code {} has {} passengers", flightCode, passengerCount);
        return passengerCount >= flightProperties.getMaxPassengers();
    }
}