package com.example.demo.DTO;

import model.Ocena;

public class OcenaResponseDTO {
	private long id;
	private int vrednost;
	private String korisnickoIme;
	private Long komentarId;

	public OcenaResponseDTO() {
	}

	public OcenaResponseDTO(Ocena o) {
		this.id = o.getId();
		this.vrednost = o.getVrednost();
		this.korisnickoIme = o.getKorisnik() != null ? o.getKorisnik().getKorisnickoIme() : null;
		this.komentarId = o.getKomentar() != null ? o.getKomentar().getId() : null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public Long getKomentarId() {
		return komentarId;
	}

	public void setKomentarId(Long komentarId) {
		this.komentarId = komentarId;
	}
}