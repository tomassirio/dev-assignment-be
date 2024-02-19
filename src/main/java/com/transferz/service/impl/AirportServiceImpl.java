package com.transferz.service.impl;

import com.transferz.dao.Airport;
import com.transferz.repository.AirportRepository;
import com.transferz.service.AirportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Page<Airport> findAllAirports(Pageable pageable) {
        log.info("Fetching all airports with pageable: {}", pageable);
        Page<Airport> airports = airportRepository.findAll(pageable);
        log.info("Returning Page of Airports. Total elements: {}", airports.getTotalElements());
        return airports;
    }
    @Override
    public Airport addAirport(@Valid Airport airport) {
        log.info("Adding airport {}", airport);
        Airport savedAirport = airportRepository.save(airport);
        log.info("Added airport with id {}", savedAirport.getAirportId());
        return savedAirport;
    }

    @Override
    public Optional<Airport> getAirport(String code) {
        log.info("Fetching airport with code {}", code);
        Optional<Airport> airport = airportRepository.findByCode(code);
        if(airport.isPresent()) {
            log.info("Found airport with code {}", code);
        } else {
            log.warn("No airport found with code {}", code);
        }
        return airport;
    }
}