package com.transferz.controller.dto;

import com.transferz.dao.Airport;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AirportDTO {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "code is mandatory")
    private String code;
    @NotBlank(message = "country is mandatory")
    private String country;

    public Airport toEntity() {
        return new Airport(this.code, this.name, this.country);
    }
}