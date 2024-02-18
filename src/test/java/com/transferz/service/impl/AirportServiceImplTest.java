package com.transferz.service.impl;

import com.transferz.dao.Airport;
import com.transferz.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AirportServiceImplTest {

    @Mock
    private AirportRepository airportRepository;
    private AirportServiceImpl airportService;
    private Validator validator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        airportService = new AirportServiceImpl(airportRepository);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void findAllAirports_returns_page_size_of_airports() {
        Airport airport1 = new Airport();
        Airport airport2 = new Airport();

        List<Airport> airports = Arrays.asList(airport1, airport2);
        Page<Airport> airportPage = new PageImpl<>(airports);
        Pageable pageable = PageRequest.of(0, 10);

        when(airportRepository.findAll(pageable)).thenReturn(airportPage);

        Page<Airport> result = airportService.findAllAirports(pageable);
        assertEquals(airportPage, result);
    }

    @Test
    void addAirport_adds_airport_to_repository() {
        Airport airport = new Airport();

        when(airportRepository.save(airport)).thenReturn(airport);

        Airport result = airportService.addAirport(airport);
        assertEquals(airport, result);
    }

    @Test
    void findAllAirports_noAirports_returns_empty_page() {
        Pageable pageable = PageRequest.of(0, 10);
        when(airportRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<Airport> result = airportService.findAllAirports(pageable);
        assertEquals(Page.empty(), result);
    }

    @Test
    void addAirport_unsuccessfulSave() {
        Airport airport = new Airport();
        when(airportRepository.save(airport)).thenReturn(null);
        Airport result = airportService.addAirport(airport);
        assertNull(result);
    }

    @Test
    void findAllAirports_invalidPageable() {
        assertThrows(IllegalArgumentException.class, () -> airportService.findAllAirports(PageRequest.of(-1, -1)));
    }

    @Test
    public void addAirport_WithValidAirport_SavesAirport() {
        Airport airport = new Airport("JFK", "John F Kennedy International", "USA");

        when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        Airport savedAirport = airportService.addAirport(airport);

        assertEquals("JFK", savedAirport.getCode());
        assertEquals("John F Kennedy International", savedAirport.getName());
        assertEquals("USA", savedAirport.getCountry());
    }

    @Test
    public void addAirport_WithInvalidAirport_ReturnsConstraintViolations() {
        Airport invalidAirport = new Airport("", "", "");

        Set<ConstraintViolation<Airport>> violations = validator.validate(invalidAirport);

        assertEquals(3, violations.size());
    }
}