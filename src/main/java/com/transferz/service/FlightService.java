package com.transferz.service;

import com.transferz.dao.Flight;

import java.util.Optional;

public interface FlightService {
    Flight addFlight(Flight flight);
    Optional<Flight> getFlight(String code);
}
