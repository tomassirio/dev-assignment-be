package com.transferz.controller.dto;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import lombok.Data;
import org.springframework.data.util.Pair;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class FlightDTO {
    @NotBlank(message = "code is mandatory")
    private String code;
    @NotBlank(message = "origin airport code is mandatory")
    private String originAirportCode;
    @NotBlank(message = "destination airport code is mandatory")
    private String destinationAirportCode;
    @NotBlank(message = "departure time is mandatory")
    private String departureTime;
    @NotBlank(message = "arrival time is mandatory")
    private String arrivalTime;
    public Flight toEntity(Pair<Airport, Airport> airports) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new Flight(this.code, airports.getFirst(), airports.getSecond(),
                LocalDateTime.parse(this.departureTime, formatter),
                LocalDateTime.parse(this.arrivalTime, formatter));
    }

}
