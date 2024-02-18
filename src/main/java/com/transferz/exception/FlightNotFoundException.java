package com.transferz.exception;

import java.io.Serial;

public class FlightNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FlightNotFoundException(String code) {
        super("Flight with code " + code + " was not found.");
    }
}