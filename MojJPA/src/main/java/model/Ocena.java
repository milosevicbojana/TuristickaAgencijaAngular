package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the ocena database table.
 *
 */
@Entity
@NamedQuery(name="Ocena.findAll", query="SELECT o FROM Ocena o")
public class Ocena implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private int vrednost;

	//bi-directional many-to-one association to Aranzman
	@ManyToOne
	private Aranzman aranzman;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	//veza ka tacno onom komentaru uz koji je ocena data
	@ManyToOne
	@JoinColumn(name = "komentar_id")
	private Komentar komentar;

	public Ocena() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVrednost() {
		return this.vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
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

	public Komentar getKomentar() {
		return this.komentar;
	}

	public void setKomentar(Komentar komentar) {
		this.komentar = komentar;
	}

}