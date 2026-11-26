package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the kategorija database table.
 * 
 */
@Entity
@NamedQuery(name="Kategorija.findAll", query="SELECT k FROM Kategorija k")
public class Kategorija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String naziv;

	//bi-directional many-to-one association to Aranzman
	@OneToMany(mappedBy="kategorija")
	private List<Aranzman> aranzmans;

	public Kategorija() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Aranzman> getAranzmans() {
		return this.aranzmans;
	}

	public void setAranzmans(List<Aranzman> aranzmans) {
		this.aranzmans = aranzmans;
	}

	public Aranzman addAranzman(Aranzman aranzman) {
		getAranzmans().add(aranzman);
		aranzman.setKategorija(this);

		return aranzman;
	}

	public Aranzman removeAranzman(Aranzman aranzman) {
		getAranzmans().remove(aranzman);
		aranzman.setKategorija(null);

		return aranzman;
	}

}