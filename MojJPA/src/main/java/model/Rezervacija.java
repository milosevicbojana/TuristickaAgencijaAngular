package model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rezervacija database table.
 * 
 */
@Entity
@NamedQuery(name="Rezervacija.findAll", query="SELECT r FROM Rezervacija r")
public class Rezervacija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="broj_osoba")
	private int brojOsoba;

	@Column(name="datum_rezervacije")
	private Timestamp datumRezervacije;

	private String status;
	
	@Column(name = "ukupna_cena")
	private BigDecimal ukupnaCena;

	//bi-directional many-to-one association to Aranzman
	@ManyToOne
	private Aranzman aranzman;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Rezervacija() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getBrojOsoba() {
		return this.brojOsoba;
	}

	public void setBrojOsoba(int brojOsoba) {
		this.brojOsoba = brojOsoba;
	}

	public Timestamp getDatumRezervacije() {
		return this.datumRezervacije;
	}

	public void setDatumRezervacije(Timestamp datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public String getStatus() {
		return this.status;
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

	public Aranzman getAranzman() {
		return this.aranzman;
	}

	public void setAranzman(Aranzman aranzman) {
		this.aranzman = aranzman;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}