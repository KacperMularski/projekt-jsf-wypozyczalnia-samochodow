package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the placowka database table.
 * 
 */
@Entity
@NamedQuery(name="Placowka.findAll", query="SELECT p FROM Placowka p")
public class Placowka implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_placowki")
	private int idPlacowki;

	private String aktywny;

	@Column(name="kod_pocz")
	private String kodPocz;

	private String miasto;

	private String ulica;

	@Lob
	private String uwagi;

	//bi-directional many-to-one association to Pojazd
	@OneToMany(mappedBy="placowka")
	private List<Pojazd> pojazds;

	//bi-directional many-to-one association to Pracownik
	@OneToMany(mappedBy="placowka")
	private List<Pracownik> pracowniks;

	//bi-directional many-to-one association to Wypozyczenie
	@OneToMany(mappedBy="placowka")
	private List<Wypozyczenie> wypozyczenies;

	public Placowka() {
	}

	public int getIdPlacowki() {
		return this.idPlacowki;
	}

	public void setIdPlacowki(int idPlacowki) {
		this.idPlacowki = idPlacowki;
	}

	public String getAktywny() {
		return this.aktywny;
	}

	public void setAktywny(String aktywny) {
		this.aktywny = aktywny;
	}

	public String getKodPocz() {
		return this.kodPocz;
	}

	public void setKodPocz(String kodPocz) {
		this.kodPocz = kodPocz;
	}

	public String getMiasto() {
		return this.miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getUlica() {
		return this.ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public List<Pojazd> getPojazds() {
		return this.pojazds;
	}

	public void setPojazds(List<Pojazd> pojazds) {
		this.pojazds = pojazds;
	}

	public Pojazd addPojazd(Pojazd pojazd) {
		getPojazds().add(pojazd);
		pojazd.setPlacowka(this);

		return pojazd;
	}

	public Pojazd removePojazd(Pojazd pojazd) {
		getPojazds().remove(pojazd);
		pojazd.setPlacowka(null);

		return pojazd;
	}

	public List<Pracownik> getPracowniks() {
		return this.pracowniks;
	}

	public void setPracowniks(List<Pracownik> pracowniks) {
		this.pracowniks = pracowniks;
	}

	public Pracownik addPracownik(Pracownik pracownik) {
		getPracowniks().add(pracownik);
		pracownik.setPlacowka(this);

		return pracownik;
	}

	public Pracownik removePracownik(Pracownik pracownik) {
		getPracowniks().remove(pracownik);
		pracownik.setPlacowka(null);

		return pracownik;
	}

	public List<Wypozyczenie> getWypozyczenies() {
		return this.wypozyczenies;
	}

	public void setWypozyczenies(List<Wypozyczenie> wypozyczenies) {
		this.wypozyczenies = wypozyczenies;
	}

	public Wypozyczenie addWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().add(wypozyczeny);
		wypozyczeny.setPlacowka(this);

		return wypozyczeny;
	}

	public Wypozyczenie removeWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().remove(wypozyczeny);
		wypozyczeny.setPlacowka(null);

		return wypozyczeny;
	}

}