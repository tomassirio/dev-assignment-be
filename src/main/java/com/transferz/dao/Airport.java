package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "airports")
public class Airport {
	@Id
	@Column(name = "airport_id")
	private UUID airportId;

	@NotBlank(message = "Code is mandatory")
	@Size(max = 20, message = "Code must not be more than 20 characters")
	@Column(unique = true, nullable = false)
	private String code;

	@NotBlank(message = "Name is mandatory")
	@Size(max = 255, message = "Code must not be more than 255 characters")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Country is mandatory")
	@Size(max = 60, message = "Country must not be more than 60 characters")
	@Column(nullable = false)
	private String country;

	public Airport(String code, String name, String country) {
		this.airportId = UUID.randomUUID();
		this.code = code;
		this.name = name;
		this.country = country;
	}
}