package com.transferz.controller;

import com.transferz.controller.dto.AirportDTO;
import com.transferz.dao.Airport;
import com.transferz.service.AirportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<Page<Airport>> getAllAirports(Pageable pageable) {
        return ResponseEntity.ok(airportService.findAllAirports(pageable));
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody AirportDTO airportDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(airportService.addAirport(airportDTO.toEntity()));
    }
}