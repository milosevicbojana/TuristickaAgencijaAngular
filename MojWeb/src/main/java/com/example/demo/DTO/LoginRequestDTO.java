package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

	@NotBlank(message = "Korisničko ime je obavezno.")
	private String korisnickoIme;

	@NotBlank(message = "Lozinka je obavezna.")
	private String lozinka;

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
}
