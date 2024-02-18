package com.transferz.service.impl;

import com.transferz.dao.Flight;
import com.transferz.repository.FlightRepository;
import com.transferz.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(@Valid Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Optional<Flight> getFlight(String code) {
        return flightRepository.findByCode(code);
    }
}