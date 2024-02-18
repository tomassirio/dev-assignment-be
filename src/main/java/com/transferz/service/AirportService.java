package com.transferz.service;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {
    Page<Airport> findAllAirports(Pageable pageable);
    Airport addAirport(Airport airport);
}