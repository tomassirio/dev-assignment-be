CREATE TABLE passengers
(
    passenger_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(1024) NOT NULL,
    flight_code  VARCHAR(20)   NOT NULL,
    FOREIGN KEY (flight_code) REFERENCES flights (code)
)