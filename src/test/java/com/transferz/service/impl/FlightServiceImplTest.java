package com.transferz.service.impl;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FlightServiceImplTest {

    private FlightServiceImpl flightService;
    private Validator validator;

    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        flightService = new FlightServiceImpl(flightRepository);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addFlight_WithValidFlight_SavesFlight() {
        Airport originAirport = new Airport("JFK", "John F Kennedy International", "USA");
        Airport destAirport = new Airport("LAX", "Los Angeles International", "USA");
        Flight flight = new Flight("FN123", originAirport, destAirport,
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(10));


        when(flightRepository.save(flight)).thenReturn(flight);

        Flight savedFlight = flightService.addFlight(flight);

        assertEquals("FN123", savedFlight.getCode());
        assertEquals(originAirport, savedFlight.getOriginAirport());
        assertEquals(destAirport, savedFlight.getDestinationAirport());
    }

    @Test
    public void addFlight_WithInvalidFlight_ReturnsConstraintViolations() {
        Flight invalidFlight = new Flight("", null, null,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().minusHours(1));

        Set<ConstraintViolation<Flight>> violations = validator.validate(invalidFlight);

        assertEquals(5, violations.size());
    }
}