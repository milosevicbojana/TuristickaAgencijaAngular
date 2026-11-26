package com.example.demo.DTO;

import java.math.BigDecimal;
import java.util.Date;

import model.Aranzman;

public class AranzmanResponseDTO {
	private long id;
	private String naziv;
	private String opis;
	private BigDecimal cena;
	private int slobodnaMesta;
	private Date datumPocetka;
	private Date datumZavrsetka;
	private Long kategorijaId;
	private String kategorijaNaziv;

	public AranzmanResponseDTO() {
	}

	public AranzmanResponseDTO(Aranzman a) {
		this.id = a.getId();
		this.naziv = a.getNaziv();
		this.opis = a.getOpis();
		this.cena = a.getCena();
		this.slobodnaMesta = a.getSlobodnaMesta();
		this.datumPocetka = a.getDatumPocetka();
		this.datumZavrsetka = a.getDatumZavrsetka();
		if (a.getKategorija() != null) {
			this.kategorijaId = a.getKategorija().getId();
			this.kategorijaNaziv = a.getKategorija().getNaziv();
		}
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public int getSlobodnaMesta() {
		return slobodnaMesta;
	}

	public void setSlobodnaMesta(int slobodnaMesta) {
		this.slobodnaMesta = slobodnaMesta;
	}

	public Date getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public Date getDatumZavrsetka() {
		return datumZavrsetka;
	}

	public void setDatumZavrsetka(Date datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}

	public Long getKategorijaId() {
		return kategorijaId;
	}

	public void setKategorijaId(Long kategorijaId) {
		this.kategorijaId = kategorijaId;
	}

	public String getKategorijaNaziv() {
		return kategorijaNaziv;
	}

	public void setKategorijaNaziv(String kategorijaNaziv) {
		this.kategorijaNaziv = kategorijaNaziv;
	}
}
