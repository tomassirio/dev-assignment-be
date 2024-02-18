package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passengers")
public class Passenger {
	@Id
	@Column(name = "passenger_id")
	private UUID passengerId;

	@NotBlank(message = "Name is mandatory")
	@Size(max = 255, message = "Name must not be more than 255 characters")
	@Column(nullable = false)
	private String name;

	@ManyToOne
	@NotNull(message = "Flight is mandatory")
	@JoinColumn(name = "flight_code", referencedColumnName = "code", nullable = false)
	private Flight flight;

	public Passenger(String name, Flight flight) {
		this.passengerId = UUID.randomUUID();
		this.name = name;
		this.flight = flight;
	}
}