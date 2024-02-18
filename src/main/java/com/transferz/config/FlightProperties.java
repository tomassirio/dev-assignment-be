package com.transferz.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "flight")
public class FlightProperties {
    private Integer maxPassengers = 150; // Default Value
}
