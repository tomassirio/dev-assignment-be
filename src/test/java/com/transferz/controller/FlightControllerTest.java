package com.transferz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.controller.dto.FlightDTO;
import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.service.AirportService;
import com.transferz.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @MockBean
    private AirportService airportService;

    @Test
    public void testCreateFlight_withValidFlightDto() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setCode("CODE");
        flightDTO.setOriginAirportCode("CODE1");
        flightDTO.setDestinationAirportCode("CODE2");
        flightDTO.setDepartureTime(LocalDateTime.now().toString());
        flightDTO.setArrivalTime(LocalDateTime.now().plusHours(2).toString());

        Airport origin = new Airport();
        origin.setCode("CODE1");
        Airport destination = new Airport();
        destination.setCode("CODE2");

        Flight flight = new Flight();
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.of(origin));
        Mockito.when(airportService.getAirport(flightDTO.getDestinationAirportCode())).thenReturn(Optional.of(destination));
        Mockito.when(flightService.addFlight(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originAirport.code").value(origin.getCode()))
                .andExpect(jsonPath("$.destinationAirport.code").value(destination.getCode()));
    }

}