package com.transferz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightNotFoundExceptionTest {
    @Test
    public void givenFlightNotFoundException_whenGetMessage_thenCorrect() {
        Exception exception = new FlightNotFoundException("XYZ");
        assertEquals("Flight with code XYZ was not found.", exception.getMessage());
    }
}