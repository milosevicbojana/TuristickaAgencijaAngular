package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RezervacijaRequestDTO {

	@NotNull(message = "Unesite broj mesta.")
	@Positive(message = "Broj mesta mora biti veći od nule.")
	private Integer brojMesta;

	public Integer getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(Integer brojMesta) {
		this.brojMesta = brojMesta;
	}
}
