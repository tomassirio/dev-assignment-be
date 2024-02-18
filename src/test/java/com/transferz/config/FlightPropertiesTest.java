package com.transferz.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FlightPropertiesTest {

    @Autowired
    private FlightProperties flightProperties;

    @Test
    void contextLoads() {
        assertEquals(Integer.valueOf(150), flightProperties.getMaxPassengers());
    }

    @Test
    void testSetAndGetMaxPassengers() {
        FlightProperties flightProperties = new FlightProperties();
        flightProperties.setMaxPassengers(200);
        assertEquals(Integer.valueOf(200), flightProperties.getMaxPassengers(), "Max passengers should be correctly set and retrieved.");
    }

    @Configuration
    @EnableConfigurationProperties(FlightProperties.class)
    static class Config { }
}