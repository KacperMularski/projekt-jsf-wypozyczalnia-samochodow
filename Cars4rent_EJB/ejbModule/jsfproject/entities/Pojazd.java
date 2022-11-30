package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pojazd database table.
 * 
 */
@Entity
@NamedQuery(name="Pojazd.findAll", query="SELECT p FROM Pojazd p")
public class Pojazd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pojazdu")
	private int idPojazdu;

	private String aktywny;

	@Column(name="cena_doba")
	private float cenaDoba;

	@Lob
	@Column(name="img_url")
	private String imgUrl;

	@Column(name="liczba_miejsc")
	private int liczbaMiejsc;

	private String marka;

	private String model;

	@Column(name="poj_silnika")
	private int pojSilnika;

	@Column(name="rodz_paliwa")
	private String rodzPaliwa;

	@Temporal(TemporalType.DATE)
	@Column(name="rok_prod")
	private Date rokProd;

	private String skrzynia;

	@Lob
	private String uwagi;

	private String wypozyczony;

	//bi-directional many-to-one association to Placowka
	@ManyToOne
	private Placowka placowka;

	//bi-directional many-to-one association to Wypozyczenie
	@OneToMany(mappedBy="pojazd")
	private List<Wypozyczenie> wypozyczenies;

	public Pojazd() {
	}

	public int getIdPojazdu() {
		return this.idPojazdu;
	}

	public void setIdPojazdu(int idPojazdu) {
		this.idPojazdu = idPojazdu;
	}

	public String getAktywny() {
		return this.aktywny;
	}

	public void setAktywny(String aktywny) {
		this.aktywny = aktywny;
	}

	public float getCenaDoba() {
		return this.cenaDoba;
	}

	public void setCenaDoba(float cenaDoba) {
		this.cenaDoba = cenaDoba;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getLiczbaMiejsc() {
		return this.liczbaMiejsc;
	}

	public void setLiczbaMiejsc(int liczbaMiejsc) {
		this.liczbaMiejsc = liczbaMiejsc;
	}

	public String getMarka() {
		return this.marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPojSilnika() {
		return this.pojSilnika;
	}

	public void setPojSilnika(int pojSilnika) {
		this.pojSilnika = pojSilnika;
	}

	public String getRodzPaliwa() {
		return this.rodzPaliwa;
	}

	public void setRodzPaliwa(String rodzPaliwa) {
		this.rodzPaliwa = rodzPaliwa;
	}

	public Date getRokProd() {
		return this.rokProd;
	}

	public void setRokProd(Date rokProd) {
		this.rokProd = rokProd;
	}

	public String getSkrzynia() {
		return this.skrzynia;
	}

	public void setSkrzynia(String skrzynia) {
		this.skrzynia = skrzynia;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public String getWypozyczony() {
		return this.wypozyczony;
	}

	public void setWypozyczony(String wypozyczony) {
		this.wypozyczony = wypozyczony;
	}

	public Placowka getPlacowka() {
		return this.placowka;
	}

	public void setPlacowka(Placowka placowka) {
		this.placowka = placowka;
	}

	public List<Wypozyczenie> getWypozyczenies() {
		return this.wypozyczenies;
	}

	public void setWypozyczenies(List<Wypozyczenie> wypozyczenies) {
		this.wypozyczenies = wypozyczenies;
	}

	public Wypozyczenie addWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().add(wypozyczeny);
		wypozyczeny.setPojazd(this);

		return wypozyczeny;
	}

	public Wypozyczenie removeWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().remove(wypozyczeny);
		wypozyczeny.setPojazd(null);

		return wypozyczeny;
	}

}