package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aranzman database table.
 * 
 */
@Entity
@NamedQuery(name="Aranzman.findAll", query="SELECT a FROM Aranzman a")
public class Aranzman implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private BigDecimal cena;

	@Temporal(TemporalType.DATE)
	@Column(name="datum_pocetka")
	private Date datumPocetka;

	@Temporal(TemporalType.DATE)
	@Column(name="datum_zavrsetka")
	private Date datumZavrsetka;

	private String naziv;

	@Lob
	private String opis;

	@Column(name="slobodna_mesta")
	private int slobodnaMesta;

	//bi-directional many-to-one association to Kategorija
	@ManyToOne
	private Kategorija kategorija;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="aranzman", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Komentar> komentars;

	//bi-directional many-to-one association to Ocena
	@OneToMany(mappedBy="aranzman", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ocena> ocenas;

	//bi-directional many-to-one association to Rezervacija
	@OneToMany(mappedBy="aranzman", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rezervacija> rezervacijas;

	public Aranzman() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCena() {
		return this.cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public Date getDatumPocetka() {
		return this.datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public Date getDatumZavrsetka() {
		return this.datumZavrsetka;
	}

	public void setDatumZavrsetka(Date datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getSlobodnaMesta() {
		return this.slobodnaMesta;
	}

	public void setSlobodnaMesta(int slobodnaMesta) {
		this.slobodnaMesta = slobodnaMesta;
	}

	public Kategorija getKategorija() {
		return this.kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public List<Komentar> getKomentars() {
		return this.komentars;
	}

	public void setKomentars(List<Komentar> komentars) {
		this.komentars = komentars;
	}

	public Komentar addKomentar(Komentar komentar) {
		getKomentars().add(komentar);
		komentar.setAranzman(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setAranzman(null);

		return komentar;
	}

	public List<Ocena> getOcenas() {
		return this.ocenas;
	}

	public void setOcenas(List<Ocena> ocenas) {
		this.ocenas = ocenas;
	}

	public Ocena addOcena(Ocena ocena) {
		getOcenas().add(ocena);
		ocena.setAranzman(this);

		return ocena;
	}

	public Ocena removeOcena(Ocena ocena) {
		getOcenas().remove(ocena);
		ocena.setAranzman(null);

		return ocena;
	}

	public List<Rezervacija> getRezervacijas() {
		return this.rezervacijas;
	}

	public void setRezervacijas(List<Rezervacija> rezervacijas) {
		this.rezervacijas = rezervacijas;
	}

	public Rezervacija addRezervacija(Rezervacija rezervacija) {
		getRezervacijas().add(rezervacija);
		rezervacija.setAranzman(this);

		return rezervacija;
	}

	public Rezervacija removeRezervacija(Rezervacija rezervacija) {
		getRezervacijas().remove(rezervacija);
		rezervacija.setAranzman(null);

		return rezervacija;
	}

}