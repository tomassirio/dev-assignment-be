package com.transferz.controller.dto;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PassengerDTO {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "flight code is mandatory")
    private String flightCode;

    public Passenger toEntity(Flight flight) {
        return new Passenger(this.name, flight);
    }
}


