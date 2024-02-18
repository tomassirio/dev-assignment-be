package com.transferz.dao;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FlightTest {
    @Test
    void testFlightConstructor() {
        String code = "code";
        Airport origin = new Airport();
        origin.setCode("OCode");
        origin.setCountry("OCountry");
        origin.setName("OName");

        Airport destination = new Airport();
        destination.setCode("DCode");
        destination.setCountry("DCountry");
        destination.setName("DName");

        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(2);

        Flight flight = new Flight(code, origin, destination, departureTime, arrivalTime);

        assertThat(flight.getFlightId()).isNotNull();
        assertThat(flight.getCode()).isEqualTo(code);
        assertThat(flight.getOriginAirport()).isEqualTo(origin);
        assertThat(flight.getDestinationAirport()).isEqualTo(destination);
        assertThat(flight.getDepartureTime()).isEqualTo(departureTime);
        assertThat(flight.getArrivalTime()).isEqualTo(arrivalTime);

    }

}