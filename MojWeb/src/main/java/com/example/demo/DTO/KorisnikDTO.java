package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import model.Uloga;

public class KorisnikDTO {
	
	@NotNull//(message="Korisnicko ime ne sme biti null.")
	@NotBlank(message="Korisnicko ime ne sme biti prazan.")
	@Size(min = 2, max = 50, message = "Korisnicko ime mora imati između 2 i 50 karaktera.")
	private String korisnickoIme;
	
	@NotNull(message="Email ne sme biti null.")
	@NotBlank(message="Email ne sme biti prazan.")
	@Email(message = "Email mora biti u ispravnom formatu (npr. korisnik@domen.com).")
	private String email;
	
	@NotNull//(message = "Lozinka ne sme biti null.")
	@NotBlank(message = "Lozinka ne sme biti prazan.")
	@Size(min = 8, message = "Lozinka mora imati najmanje 8 karaktera.")
	@Pattern(
		    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
		    message = "Lozinka mora sadržati bar jedno veliko slovo, jedno malo slovo i jedan broj."
		)
	private String lozinka;
	
	@NotNull(message="Izaberi ulogu.")
	private Uloga uloga;

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
}
