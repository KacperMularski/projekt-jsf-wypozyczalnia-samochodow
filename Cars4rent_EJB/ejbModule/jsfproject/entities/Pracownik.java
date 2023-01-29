package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pracownik database table.
 * 
 */
@Entity
@NamedQuery(name="Pracownik.findAll", query="SELECT p FROM Pracownik p")
public class Pracownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pracownika")
	private int idPracownika;

	private String aktywny;

	@Temporal(TemporalType.DATE)
	@Column(name="data_ur")
	private Date dataUr;

	@Temporal(TemporalType.DATE)
	@Column(name="data_zatrud")
	private Date dataZatrud;

	private String imie;

	@Column(name="kod_pocz")
	private String kodPocz;

	private String miasto;

	private String nazwisko;

	@Column(name="nr_tel")
	private String nrTel;

	@Column(name="ulica_nr_domu")
	private String ulicaNrDomu;

	@Lob
	private String uwagi;

	//bi-directional many-to-one association to Placowka
	@ManyToOne
	private Placowka placowka;

	//bi-directional one-to-one association to Konto
	@OneToOne
	private Konto konto;

	public Pracownik() {
	}

	public int getIdPracownika() {
		return this.idPracownika;
	}

	public void setIdPracownika(int idPracownika) {
		this.idPracownika = idPracownika;
	}

	public String getAktywny() {
		return this.aktywny;
	}

	public void setAktywny(String aktywny) {
		this.aktywny = aktywny;
	}

	public Date getDataUr() {
		return this.dataUr;
	}

	public void setDataUr(Date dataUr) {
		this.dataUr = dataUr;
	}

	public Date getDataZatrud() {
		return this.dataZatrud;
	}

	public void setDataZatrud(Date dataZatrud) {
		this.dataZatrud = dataZatrud;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
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

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNrTel() {
		return this.nrTel;
	}

	public void setNrTel(String nrTel) {
		this.nrTel = nrTel;
	}

	public String getUlicaNrDomu() {
		return this.ulicaNrDomu;
	}

	public void setUlicaNrDomu(String ulicaNrDomu) {
		this.ulicaNrDomu = ulicaNrDomu;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public Placowka getPlacowka() {
		return this.placowka;
	}

	public void setPlacowka(Placowka placowka) {
		this.placowka = placowka;
	}

	public Konto getKonto() {
		return this.konto;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

}