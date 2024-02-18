package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flights")
public class Flight {

	@Id
	@Column(name = "flight_id")
	private UUID flightId;

	@NotBlank(message = "Code is mandatory")
	@Size(max = 20, message = "Code must not be more than 20 characters")
	@Column(nullable = false)
	private String code;

	@NotNull(message = "Origin airport is mandatory")
	@ManyToOne
	@JoinColumn(name = "origin_airport_code", referencedColumnName = "code", nullable = false)
	private Airport originAirport;

	@NotNull(message = "Destination airport is mandatory")
	@ManyToOne
	@JoinColumn(name = "destination_airport_code", referencedColumnName = "code", nullable = false)
	private Airport destinationAirport;

	@Future(message = "Departure time should be in the future")
	@Column(name = "departure_time", nullable = false)
	private LocalDateTime departureTime;

	@Future(message = "Arrival time should be in the future")
	@Column(name = "arrival_time", nullable = false)
	private LocalDateTime arrivalTime;

	public Flight(String code, Airport originAirport, Airport destinationAirport, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		this.flightId = UUID.randomUUID();
		this.code = code;
		this.originAirport = originAirport;
		this.destinationAirport = destinationAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
}