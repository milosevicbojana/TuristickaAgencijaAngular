package com.example.demo.DTO;

import model.Korisnik;

public class KorisnikResponseDTO {
	private long id;
	private String korisnickoIme;
	private String email;
	private String uloga;

	public KorisnikResponseDTO() {
	}

	public KorisnikResponseDTO(Korisnik k) {
		this.id = k.getId();
		this.korisnickoIme = k.getKorisnickoIme();
		this.email = k.getEmail();
		this.uloga = k.getUloga() != null ? k.getUloga().getNaziv() : null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
}
