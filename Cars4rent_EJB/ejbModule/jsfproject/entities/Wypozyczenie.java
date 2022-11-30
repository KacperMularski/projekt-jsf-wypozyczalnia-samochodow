package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the wypozyczenie database table.
 * 
 */
@Entity
@NamedQuery(name="Wypozyczenie.findAll", query="SELECT w FROM Wypozyczenie w")
public class Wypozyczenie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_wypozyczenia")
	private int idWypozyczenia;

	private String aktywny;

	@Temporal(TemporalType.DATE)
	@Column(name="data_wyp")
	private Date dataWyp;

	@Temporal(TemporalType.DATE)
	@Column(name="data_zw")
	private Date dataZw;

	@Column(name="forma_plat")
	private String formaPlat;

	@Lob
	private String uwagi;

	@Column(name="wart_wyp")
	private float wartWyp;

	//bi-directional many-to-one association to Klient
	@ManyToOne
	private Klient klient;

	//bi-directional many-to-one association to Placowka
	@ManyToOne
	private Placowka placowka;

	//bi-directional many-to-one association to Pojazd
	@ManyToOne
	private Pojazd pojazd;

	public Wypozyczenie() {
	}

	public int getIdWypozyczenia() {
		return this.idWypozyczenia;
	}

	public void setIdWypozyczenia(int idWypozyczenia) {
		this.idWypozyczenia = idWypozyczenia;
	}

	public String getAktywny() {
		return this.aktywny;
	}

	public void setAktywny(String aktywny) {
		this.aktywny = aktywny;
	}

	public Date getDataWyp() {
		return this.dataWyp;
	}

	public void setDataWyp(Date dataWyp) {
		this.dataWyp = dataWyp;
	}

	public Date getDataZw() {
		return this.dataZw;
	}

	public void setDataZw(Date dataZw) {
		this.dataZw = dataZw;
	}

	public String getFormaPlat() {
		return this.formaPlat;
	}

	public void setFormaPlat(String formaPlat) {
		this.formaPlat = formaPlat;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public float getWartWyp() {
		return this.wartWyp;
	}

	public void setWartWyp(float wartWyp) {
		this.wartWyp = wartWyp;
	}

	public Klient getKlient() {
		return this.klient;
	}

	public void setKlient(Klient klient) {
		this.klient = klient;
	}

	public Placowka getPlacowka() {
		return this.placowka;
	}

	public void setPlacowka(Placowka placowka) {
		this.placowka = placowka;
	}

	public Pojazd getPojazd() {
		return this.pojazd;
	}

	public void setPojazd(Pojazd pojazd) {
		this.pojazd = pojazd;
	}

}