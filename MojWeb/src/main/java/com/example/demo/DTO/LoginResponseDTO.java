package com.example.demo.DTO;

public class LoginResponseDTO {
	private String token;
	private long id;
	private String korisnickoIme;
	private String uloga;

	public LoginResponseDTO(String token, long id, String korisnickoIme, String uloga) {
		this.token = token;
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.uloga = uloga;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
}
