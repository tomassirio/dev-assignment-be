package com.transferz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AirportNotFoundExceptionTest {
    @Test
    public void givenAirportNotFoundException_whenGetMessage_thenCorrect() {
        Exception exception = new AirportNotFoundException("ABC");
        assertEquals("Airport with code ABC was not found.", exception.getMessage());
    }

}