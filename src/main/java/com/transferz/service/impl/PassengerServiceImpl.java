package com.transferz.service.impl;

import com.transferz.config.FlightProperties;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.exception.AvailableFlightNotFoundException;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

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
        Flight flight = passenger.getFlight();

        if (isFlightFull(flight.getCode())) {
            flightRepository.findAvailableFlight(flightProperties.getMaxPassengers())
                .ifPresentOrElse(passenger::setFlight, () -> {
                    throw new AvailableFlightNotFoundException();
                });
        }

        return passengerRepository.save(passenger);
    }

    private boolean isFlightFull(String flightCode) {
        return passengerRepository.countPassengerByFlightCode(flightCode) >= flightProperties.getMaxPassengers();
    }
}