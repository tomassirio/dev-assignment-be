package com.transferz.controller.dto;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import lombok.Data;

@Data
public class PassengerDTO {
    private String name;
    private String flightCode;

    public Passenger toEntity(Flight flight) {
        return new Passenger(this.name, flight);
    }
}


