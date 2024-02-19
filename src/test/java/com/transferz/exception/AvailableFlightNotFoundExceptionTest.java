package com.transferz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvailableFlightNotFoundExceptionTest {
    @Test
    public void givenAvailableFlightNotFoundException_whenGetMessage_thenCorrect() {
        Exception exception = new AvailableFlightNotFoundException();
        assertEquals("No available flights", exception.getMessage());
    }
}