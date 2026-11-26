package com.example.demo.DTO;

import model.Kategorija;

public class KategorijaResponseDTO {
	private long id;
	private String naziv;

	public KategorijaResponseDTO() {
	}

	public KategorijaResponseDTO(Kategorija k) {
		this.id = k.getId();
		this.naziv = k.getNaziv();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
}
