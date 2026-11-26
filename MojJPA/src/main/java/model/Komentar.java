package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the komentar database table.
 * 
 */
@Entity
@NamedQuery(name="Komentar.findAll", query="SELECT k FROM Komentar k")
public class Komentar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="datum_kreiranja")
	private Timestamp datumKreiranja;

	@Lob
	private String sadrzaj;

	//bi-directional many-to-one association to Aranzman
	@ManyToOne
	private Aranzman aranzman;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Komentar() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getDatumKreiranja() {
		return this.datumKreiranja;
	}

	public void setDatumKreiranja(Timestamp datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public String getSadrzaj() {
		return this.sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
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