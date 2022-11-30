package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the klient database table.
 * 
 */
@Entity
@NamedQuery(name="Klient.findAll", query="SELECT k FROM Klient k")
public class Klient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_klienta")
	private int idKlienta;

	private String aktywny;

	@Temporal(TemporalType.DATE)
	@Column(name="data_ur")
	private Date dataUr;

	private String imie;

	private String nazwisko;

	@Column(name="nr_pr_jazdy")
	private String nrPrJazdy;

	@Column(name="nr_tel")
	private String nrTel;

	@Lob
	private String uwagi;

	//bi-directional many-to-one association to Konto
	@ManyToOne
	private Konto konto;

	//bi-directional many-to-one association to Wypozyczenie
	@OneToMany(mappedBy="klient")
	private List<Wypozyczenie> wypozyczenies;

	public Klient() {
	}

	public int getIdKlienta() {
		return this.idKlienta;
	}

	public void setIdKlienta(int idKlienta) {
		this.idKlienta = idKlienta;
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

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNrPrJazdy() {
		return this.nrPrJazdy;
	}

	public void setNrPrJazdy(String nrPrJazdy) {
		this.nrPrJazdy = nrPrJazdy;
	}

	public String getNrTel() {
		return this.nrTel;
	}

	public void setNrTel(String nrTel) {
		this.nrTel = nrTel;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public Konto getKonto() {
		return this.konto;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public List<Wypozyczenie> getWypozyczenies() {
		return this.wypozyczenies;
	}

	public void setWypozyczenies(List<Wypozyczenie> wypozyczenies) {
		this.wypozyczenies = wypozyczenies;
	}

	public Wypozyczenie addWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().add(wypozyczeny);
		wypozyczeny.setKlient(this);

		return wypozyczeny;
	}

	public Wypozyczenie removeWypozyczeny(Wypozyczenie wypozyczeny) {
		getWypozyczenies().remove(wypozyczeny);
		wypozyczeny.setKlient(null);

		return wypozyczeny;
	}

}