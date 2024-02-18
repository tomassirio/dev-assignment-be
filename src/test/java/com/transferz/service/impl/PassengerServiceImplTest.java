package com.transferz.service.impl;

import com.transferz.config.FlightProperties;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PassengerServiceImplTest {
    private PassengerServiceImpl passengerService;
    private Validator validator;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightProperties flightProperties;

    private static final Integer MAX_PASSENGERS = 150;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(flightProperties.getMaxPassengers()).thenReturn(MAX_PASSENGERS);

        passengerService = new PassengerServiceImpl(passengerRepository, flightRepository, flightProperties);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addPassenger_WithValidPassenger_SavesPassenger() {
        Flight flight = new Flight("FN123", null, null, null, null);
        Passenger passenger = new Passenger("John Doe", flight);
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);
        when(passengerRepository.countPassengerByFlightCode(flight.getCode())).thenReturn(MAX_PASSENGERS - 1);

        Passenger savedPassenger = passengerService.addPassenger(passenger);

        assertEquals("John Doe", savedPassenger.getName());
        assertEquals(flight, savedPassenger.getFlight());
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    public void addPassenger_WithValidPassengerButFlightIsFull_SavesPassengerWithAvailableFlight() {
        Flight flight = new Flight("FN123", null, null, null, null);
        Flight availableFlight = new Flight("FN124", null, null, null, null);
        Passenger passenger = new Passenger("John Doe", flight);

        when(passengerRepository.countPassengerByFlightCode(flight.getCode())).thenReturn(MAX_PASSENGERS);
        when(flightRepository.findAvailableFlight(MAX_PASSENGERS)).thenReturn(Optional.of(availableFlight));
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger savedPassenger = passengerService.addPassenger(passenger);

        
        assertEquals(availableFlight, savedPassenger.getFlight());
    }


    @Test
    public void addPassenger_WithValidPassengerButAllFlightsAreFull_ThrowsRuntimeException() {
        Flight flight = new Flight("FN123", null, null, null, null);
        Passenger passenger = new Passenger("John Doe", flight);

        when(passengerRepository.countPassengerByFlightCode(flight.getCode())).thenReturn(MAX_PASSENGERS);
        when(flightRepository.findAvailableFlight(MAX_PASSENGERS)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> passengerService.addPassenger(passenger));
    }
    @Test
    public void addPassenger_WithInvalidPassenger_ReturnsConstraintViolations() {
        Passenger invalidPassenger = new Passenger("", null);

        Set<ConstraintViolation<Passenger>> violations = validator.validate(invalidPassenger);

        assertEquals(2, violations.size());
    }
}