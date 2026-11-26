package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public class StatusRequestDTO {

	@NotBlank(message = "Status je obavezan.")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
