package com.example.demo.DTO;

import java.sql.Timestamp;

import model.Komentar;

public class KomentarResponseDTO {
	private long id;
	private String sadrzaj;
	private Timestamp datumKreiranja;
	private String korisnickoIme;

	public KomentarResponseDTO() {
	}

	public KomentarResponseDTO(Komentar k) {
		this.id = k.getId();
		this.sadrzaj = k.getSadrzaj();
		this.datumKreiranja = k.getDatumKreiranja();
		this.korisnickoIme = k.getKorisnik() != null ? k.getKorisnik().getKorisnickoIme() : null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public Timestamp getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(Timestamp datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
}
