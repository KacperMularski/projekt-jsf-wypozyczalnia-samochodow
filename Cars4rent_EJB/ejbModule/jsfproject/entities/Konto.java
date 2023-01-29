package jsfproject.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the konto database table.
 * 
 */
@Entity
@NamedQuery(name="Konto.findAll", query="SELECT k FROM Konto k")
public class Konto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_konta")
	private int idKonta;

	private String aktywny;

	private String email;

	private String haslo;

	private String login;

	private String rola;

	@Lob
	private String uwagi;

	//bi-directional many-to-one association to Klient
	@OneToMany(mappedBy="konto")
	private List<Klient> klients;

	//bi-directional one-to-one association to Pracownik
	@OneToOne(mappedBy="konto")
	private Pracownik pracownik;

	public Konto() {
	}

	public int getIdKonta() {
		return this.idKonta;
	}

	public void setIdKonta(int idKonta) {
		this.idKonta = idKonta;
	}

	public String getAktywny() {
		return this.aktywny;
	}

	public void setAktywny(String aktywny) {
		this.aktywny = aktywny;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRola() {
		return this.rola;
	}

	public void setRola(String rola) {
		this.rola = rola;
	}

	public String getUwagi() {
		return this.uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public List<Klient> getKlients() {
		return this.klients;
	}

	public void setKlients(List<Klient> klients) {
		this.klients = klients;
	}

	public Klient addKlient(Klient klient) {
		getKlients().add(klient);
		klient.setKonto(this);

		return klient;
	}

	public Klient removeKlient(Klient klient) {
		getKlients().remove(klient);
		klient.setKonto(null);

		return klient;
	}

	public Pracownik getPracownik() {
		return this.pracownik;
	}

	public void setPracownik(Pracownik pracownik) {
		this.pracownik = pracownik;
	}

}