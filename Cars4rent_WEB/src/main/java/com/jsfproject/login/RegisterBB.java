package com.jsfproject.login;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import jsfproject.DAO.KontoDAO;
import jsfproject.entities.Konto;
import jsfproject.DAO.KlientDAO;
import jsfproject.entities.Klient;

@Named
@RequestScoped
public class RegisterBB {
	private static final String PAGE_MAIN = "/public/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private String login;
	private String email;
	private String haslo;
	private String haslo_rep;
	private String imie;
	private String nazwisko;
	private Date data_ur;
	private String nr_pr_jazdy;
	private String nr_tel;
	
	private Konto registerCheck;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHaslo() {
		return haslo;
	}
	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	public String getHaslo_rep() {
		return haslo_rep;
	}
	public void setHaslo_rep(String haslo_rep) {
		this.haslo_rep = haslo_rep;
	}
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public Date getData_ur() {
		return data_ur;
	}
	public void setData_ur(Date data_ur) {
		this.data_ur = data_ur;
	}
	public String getNr_pr_jazdy() {
		return nr_pr_jazdy;
	}
	public void setNr_pr_jazdy(String nr_pr_jazdy) {
		this.nr_pr_jazdy = nr_pr_jazdy;
	}
	public String getNr_tel() {
		return nr_tel;
	}
	public void setNr_tel(String nr_tel) {
		this.nr_tel = nr_tel;
	}
	
	@EJB
	KontoDAO kontoDAO;
	
	@EJB
	KlientDAO klientDAO;	
	
	FacesContext ctx = FacesContext.getCurrentInstance();
	
	public String doRegister() {	
	
		//check password repeat
		if (! haslo.equals(haslo_rep)) {
			
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Hasła muszą być identyczne", null));
						
			return PAGE_STAY_AT_THE_SAME;
			
		}	
				
		// 1. verify login and email - get User from "database" 
		registerCheck = kontoDAO.checkRegisterData(login, email);
				
		// 2. if typed login or email is already in use - stay with error info
		if (registerCheck != null) {
			
			if (registerCheck.getLogin().equals(login)) { 
				
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ten login jest już zajęty", null));				
			}
			if (registerCheck.getEmail().equals(email) ) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ten adres e-mail jest już zajęty", null));
				
			}
									
			return PAGE_STAY_AT_THE_SAME;
			
		}
		
		//Save account to the database (tables: konto, klient)
		try {
			
			Konto konto = new Konto();
			
			konto.setLogin(login);
			konto.setEmail(email);
			konto.setHaslo(haslo);
			konto.setRola("user");
			konto.setAktywny("tak");
			konto.setUwagi("Brak");
			
			kontoDAO.insert(konto);						
						
			Klient klient = new Klient();
			
			klient.setKonto(konto);
			klient.setImie(imie);
			klient.setNazwisko(nazwisko);
			klient.setDataUr(data_ur);
			klient.setNrPrJazdy(nr_pr_jazdy);
			klient.setNrTel("+48" + nr_tel);
			klient.setAktywny("tak");
			klient.setUwagi("Brak");
			
			klientDAO.insert(klient);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		
		return PAGE_MAIN;

	}
	
	
	
	
	

}
