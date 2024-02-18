CREATE TABLE flights
(
    flight_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(20) UNIQUE NOT NULL,
    origin_airport_code VARCHAR(20) NOT NULL,
    destination_airport_code VARCHAR(20) NOT NULL,
    departure_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arrival_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (origin_airport_code) REFERENCES airports (code),
    FOREIGN KEY (destination_airport_code) REFERENCES airports (code)
);