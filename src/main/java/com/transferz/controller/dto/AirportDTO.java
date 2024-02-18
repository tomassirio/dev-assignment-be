package com.transferz.controller.dto;

import com.transferz.dao.Airport;
import lombok.Data;

@Data
public class AirportDTO {
    private String name;
    private String code;
    private String country;

    public Airport toEntity() {
        return new Airport(this.code, this.name, this.country);
    }
}