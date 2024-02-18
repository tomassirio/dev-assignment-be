package com.transferz.service;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AirportService {
    Page<Airport> findAllAirports(Pageable pageable);
    Airport addAirport(Airport airport);
    Optional<Airport> getAirport(String code);
}