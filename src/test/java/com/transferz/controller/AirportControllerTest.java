package com.transferz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.controller.dto.AirportDTO;
import com.transferz.dao.Airport;
import com.transferz.service.AirportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Test
    public void testGetAllAirports_withNoAirportsInDatabase() throws Exception {
        Page<Airport> airports = new PageImpl<>(new ArrayList<>());
        Mockito.when(airportService.findAllAirports(PageRequest.of(0, 20))).thenReturn(airports);

        mockMvc.perform(get("/v1/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void testGetAllAirports_withAirportsInDatabase() throws Exception {
        List<Airport> airportList = new ArrayList<>();
        Airport airport = new Airport();
        airport.setCode("CODE");
        airport.setName("Name");
        airport.setCountry("Country");
        airportList.add(airport);
        Page<Airport> airports = new PageImpl<>(airportList);
        Mockito.when(airportService.findAllAirports(PageRequest.of(0, 20))).thenReturn(airports);

        mockMvc.perform(get("/v1/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].code").value(airport.getCode()))
                .andExpect(jsonPath("$.content[0].name").value(airport.getName()))
                .andExpect(jsonPath("$.content[0].country").value(airport.getCountry()));
    }


    @Test
    public void testCreateAirport_withValidAirportDto() throws Exception {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setCode("CODE");
        airportDTO.setName("Name");
        airportDTO.setCountry("Country");

        Mockito.when(airportService.addAirport(any(Airport.class))).thenReturn(airportDTO.toEntity());

        mockMvc.perform(post("/v1/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(airportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(airportDTO.getCode()))
                .andExpect(jsonPath("$.name").value(airportDTO.getName()))
                .andExpect(jsonPath("$.country").value(airportDTO.getCountry()));
    }

    @Test
    public void testCreateAirport_withInvalidAirportDto() throws Exception {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setCode("");
        airportDTO.setName("");
        airportDTO.setCountry("");

        mockMvc.perform(post("/v1/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(airportDTO)))
                .andExpect(status().isBadRequest());
    }

}