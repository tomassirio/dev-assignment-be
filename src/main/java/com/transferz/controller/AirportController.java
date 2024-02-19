package com.transferz.controller;

import com.transferz.controller.dto.AirportDTO;
import com.transferz.dao.Airport;
import com.transferz.service.AirportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<Page<Airport>> getAllAirports(Pageable pageable) {
        log.debug("Received request to get all airports with pageable: {}", pageable);
        Page<Airport> allAirports = airportService.findAllAirports(pageable);
        log.info("Returning Page of Airports. Total elements: {}", allAirports.getTotalElements());
        return ResponseEntity.ok(allAirports);
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@Valid @RequestBody AirportDTO airportDTO){
        log.debug("Received request to create airport: {}", airportDTO);
        Airport createdAirport = airportService.addAirport(airportDTO.toEntity());
        log.info("Created airport with id {}", createdAirport.getAirportId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirport);
    }
}