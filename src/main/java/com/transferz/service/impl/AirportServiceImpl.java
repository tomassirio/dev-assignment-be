package com.transferz.service.impl;

import com.transferz.dao.Airport;
import com.transferz.repository.AirportRepository;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Page<Airport> findAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    @Override
    public Airport addAirport(@Valid Airport airport) {
        return airportRepository.save(airport);
    }
}