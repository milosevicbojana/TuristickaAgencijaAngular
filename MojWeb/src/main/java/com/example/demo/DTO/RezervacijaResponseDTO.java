package com.example.demo.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

import model.Rezervacija;

public class RezervacijaResponseDTO {
	private long id;
	private int brojOsoba;
	private Timestamp datumRezervacije;
	private String status;
	private BigDecimal ukupnaCena;
	private Long aranzmanId;
	private String aranzmanNaziv;
	private Long korisnikId;
	private String korisnickoIme;

	public RezervacijaResponseDTO() {
	}

	public RezervacijaResponseDTO(Rezervacija r) {
		this.id = r.getId();
		this.brojOsoba = r.getBrojOsoba();
		this.datumRezervacije = r.getDatumRezervacije();
		this.status = r.getStatus();
		this.ukupnaCena = r.getUkupnaCena();
		if (r.getAranzman() != null) {
			this.aranzmanId = r.getAranzman().getId();
			this.aranzmanNaziv = r.getAranzman().getNaziv();
		}
		if (r.getKorisnik() != null) {
			this.korisnikId = r.getKorisnik().getId();
			this.korisnickoIme = r.getKorisnik().getKorisnickoIme();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getBrojOsoba() {
		return brojOsoba;
	}

	public void setBrojOsoba(int brojOsoba) {
		this.brojOsoba = brojOsoba;
	}

	public Timestamp getDatumRezervacije() {
		return datumRezervacije;
	}

	public void setDatumRezervacije(Timestamp datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(BigDecimal ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public Long getAranzmanId() {
		return aranzmanId;
	}

	public void setAranzmanId(Long aranzmanId) {
		this.aranzmanId = aranzmanId;
	}

	public String getAranzmanNaziv() {
		return aranzmanNaziv;
	}

	public void setAranzmanNaziv(String aranzmanNaziv) {
		this.aranzmanNaziv = aranzmanNaziv;
	}

	public Long getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
}
