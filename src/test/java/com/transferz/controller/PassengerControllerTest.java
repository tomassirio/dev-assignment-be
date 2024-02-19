package com.transferz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.controller.dto.PassengerDTO;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.service.FlightService;
import com.transferz.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @MockBean
    private FlightService flightService;

    @Test
    public void testCreatePassenger_withValidPassengerDto() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("John Doe");
        passengerDTO.setFlightCode("FLIGHT100");

        Flight flight = new Flight();
        flight.setCode(passengerDTO.getFlightCode());

        Passenger passenger = new Passenger();
        passenger.setName(passengerDTO.getName());
        passenger.setFlight(flight);

        Mockito.when(flightService.getFlight(passengerDTO.getFlightCode())).thenReturn(Optional.of(flight));
        Mockito.when(passengerService.addPassenger(any(Passenger.class))).thenReturn(passenger);

        mockMvc.perform(post("/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passengerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(passenger.getName()))
                .andExpect(jsonPath("$.flight.code").value(flight.getCode()));
    }

}