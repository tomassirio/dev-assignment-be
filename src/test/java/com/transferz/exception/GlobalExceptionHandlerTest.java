package com.transferz.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.controller.AirportController;
import com.transferz.controller.dto.FlightDTO;
import com.transferz.controller.dto.PassengerDTO;
import com.transferz.dao.Airport;
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
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AirportController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @MockBean
    private FlightService flightService;

    @Test
    void whenPostRequestWithInvalidAirportDto_thenRespondWithBadRequest() throws Exception {
        mockMvc.perform(
                        post("/v1/airports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestAndServiceThrowsException_thenRespondWithInternalServerError() throws Exception {
        doThrow(new RuntimeException("Test exception")).when(airportService).addAirport(any(Airport.class));

        mockMvc.perform(
                        post("/v1/airports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"code\":\"code\", \"name\":\"name\", \"country\":\"country\"}"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testCreateFlight_withNonexistentOriginAirport() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginAirportCode("NONEXISTENT_CODE");
        flightDTO.setDestinationAirportCode("CODE2");

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.empty());

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlight_withNonexistentDestinationAirport() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginAirportCode("CODE1");
        flightDTO.setDestinationAirportCode("NONEXISTENT_CODE");

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.of(new Airport()));
        Mockito.when(airportService.getAirport(flightDTO.getDestinationAirportCode())).thenReturn(Optional.empty());

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePassenger_withNonexistentFlight() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setFlightCode("NONEXISTENT_FLIGHT");

        Mockito.when(flightService.getFlight(passengerDTO.getFlightCode())).thenReturn(Optional.empty());

        mockMvc.perform(post("/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passengerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlight_withIncorrectTimeFormat() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginAirportCode("CODE1");
        flightDTO.setDestinationAirportCode("CODE2");
        flightDTO.setDepartureTime("Invalid Time Format");
        flightDTO.setArrivalTime("Invalid Time Format");

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.of(new Airport()));
        Mockito.when(airportService.getAirport(flightDTO.getDestinationAirportCode())).thenReturn(Optional.of(new Airport()));

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlight_withTimeInThePast() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginAirportCode("CODE1");
        flightDTO.setDestinationAirportCode("CODE2");
        flightDTO.setDepartureTime(LocalDateTime.now().minusDays(1).toString());
        flightDTO.setArrivalTime(LocalDateTime.now().minusDays(1).toString());

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.of(new Airport()));
        Mockito.when(airportService.getAirport(flightDTO.getDestinationAirportCode())).thenReturn(Optional.of(new Airport()));

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFlight_withSameOriginAndDestinationAirport() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginAirportCode("CODE1");
        flightDTO.setDestinationAirportCode("CODE1");
        flightDTO.setDepartureTime(LocalDateTime.now().toString());
        flightDTO.setArrivalTime(LocalDateTime.now().plusHours(2).toString());

        Mockito.when(airportService.getAirport(flightDTO.getOriginAirportCode())).thenReturn(Optional.of(new Airport()));
        Mockito.when(airportService.getAirport(flightDTO.getDestinationAirportCode())).thenReturn(Optional.of(new Airport()));

        mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isNotFound());
    }

}