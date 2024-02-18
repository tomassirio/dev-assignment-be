package com.transferz.dao;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerTest {
    @Test
    void testPassengerConstructor() {
        String name = "Tom :D";

        String flightCode = "flight1";
        Airport origin = new Airport("originCode", "originName", "originCountry");
        Airport destination = new Airport("destinationCode", "destinationName", "destinationCountry");
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(2);

        Flight flight = new Flight(flightCode, origin, destination, departureTime, arrivalTime);

        Passenger passenger = new Passenger(name, flight);

        assertThat(passenger.getPassengerId()).isNotNull();
        assertThat(passenger.getName()).isEqualTo(name);
        assertThat(passenger.getFlight()).isEqualToComparingFieldByField(flight);
    }

}