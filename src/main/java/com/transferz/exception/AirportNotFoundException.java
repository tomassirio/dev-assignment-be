package com.transferz.exception;

import java.io.Serial;

public class AirportNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AirportNotFoundException(String code) {
        super("Airport with code " + code + " was not found.");
    }
}