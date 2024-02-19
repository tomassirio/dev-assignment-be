package com.transferz.service.impl;

import com.transferz.dao.Flight;
import com.transferz.repository.FlightRepository;
import com.transferz.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(@Valid Flight flight) {
        log.info("Adding flight {}", flight);
        Flight savedFlight = flightRepository.save(flight);
        log.info("Added flight with id {}", savedFlight.getFlightId());
        return savedFlight;
    }

    @Override
    public Optional<Flight> getFlight(String code) {
        log.info("Fetching flight with code {}", code);
        Optional<Flight> flight = flightRepository.findByCode(code);
        if(flight.isPresent()) {
            log.info("Found flight with code {}", code);
        } else {
            log.warn("No flight found with code {}", code);
        }
        return flight;
    }
}