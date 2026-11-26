package com.example.demo.DTO;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import model.Kategorija;

public class AranzmanDTO {
	
	@NotNull(message = "Naziv ne sme biti prazan.")
	@Size(min = 2, max = 50, message = "Opis mora imati između 2 i 50 karaktera.")
	private String naziv;
	
	@NotNull(message = "Opis ne sme biti prazan.")
	@Size(min = 10, max = 1000, message = "Opis mora imati između 10 i 1000 karaktera.")
	private String opis;
	
	@NotNull(message = "Unesite cenu (po osobi).")
	@Positive(message = "Cena mora biti veća od nule.")
	private BigDecimal cena;
	
	@NotNull(message = "Unesite broj slobodnih mesta.")
	@Positive(message = "Broj slobodnih mesta mora biti pozitivna.")
	private Integer slobodnaMesta;
	
	@NotNull(message="Unesite datum početka.")
	@FutureOrPresent(message = "Datum početka mora biti budući.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datumPocetka;
	
	@NotNull(message="Unesite datum završetka.")
	@FutureOrPresent(message = "Datum završetka mora biti budući.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datumZavrsetka;
	
	@NotNull(message="Izaberi kategoriju.")
	private Kategorija kategorija;

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

	public Integer getSlobodnaMesta() {
		return slobodnaMesta;
	}

	public void setSlobodnaMesta(Integer slobodnaMesta) {
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

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}
}
