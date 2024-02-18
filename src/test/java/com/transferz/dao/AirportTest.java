package com.transferz.dao;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AirportTest {

    @Test
    void testAirportConstructor() {
        Airport airport = new Airport("code", "name", "country");

        assertThat(airport.getAirportId()).isNotNull();
        assertThat(airport.getAirportId()).isInstanceOf(UUID.class);

        assertThat(airport.getCode()).isEqualTo("code");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getCountry()).isEqualTo("country");
    }

}