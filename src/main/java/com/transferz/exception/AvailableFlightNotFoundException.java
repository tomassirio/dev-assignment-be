package com.transferz.exception;

import java.io.Serial;

public class AvailableFlightNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AvailableFlightNotFoundException() {
        super("No available flights");
    }
}
