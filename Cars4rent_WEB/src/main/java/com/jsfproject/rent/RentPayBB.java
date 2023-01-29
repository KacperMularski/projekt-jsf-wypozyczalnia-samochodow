package com.jsfproject.rent;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import jsfproject.DAO.KlientDAO;
import jsfproject.DAO.PlacowkaDAO;
import jsfproject.DAO.PojazdDAO;
import jsfproject.DAO.WypozyczenieDAO;
import jsfproject.entities.Klient;
import jsfproject.entities.Placowka;
import jsfproject.entities.Pojazd;
import jsfproject.entities.Wypozyczenie;

@Named
@ViewScoped
public class RentPayBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RENT_DONE = "rentDone.xhtml";
	
	FacesContext ctx = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);	
	
	private Pojazd pojazd = new Pojazd();
	private Placowka placowka = new Placowka();
	private Pojazd loaded = null;
	
	private Klient klient = new Klient();
	
	private Date dataWyp;
	private Date dataZw;
	private String dataWypSt;
	private String dataZwSt;
	private Integer idKonta;
	private String platnosc;
	private Integer placowkaGet;
	
	private Long liczbaDni;
	private Float wartosc;
	
	@EJB
	PojazdDAO pojazdDAO;
	
	@EJB
	KlientDAO klientDAO;
	
	@EJB
	WypozyczenieDAO wypozyczenieDAO;
	
	@EJB
	PlacowkaDAO placowkaDAO;

	public Pojazd getPojazd() {
		return pojazd;
	}		
	
	public Date getDataWyp() {
		return dataWyp;
	}

	public void setDataWyp(Date dataWyp) {
		this.dataWyp = dataWyp;
	}

	public Date getDataZw() {
		return dataZw;
	}

	public void setDataZw(Date dataZw) {
		this.dataZw = dataZw;
	}	

	public String getDataWypSt() {
		return dataWypSt;
	}

	public void setDataWypSt(String dataWypSt) {
		this.dataWypSt = dataWypSt;
	}

	public String getDataZwSt() {
		return dataZwSt;
	}

	public void setDataZwSt(String dataZwSt) {
		this.dataZwSt = dataZwSt;
	}

	public Integer getIdKonta() {
		return idKonta;
	}

	public void setIdKlienta(Integer idKonta) {
		this.idKonta = idKonta;
	}		

	public Klient getKlient() {
		return klient;
	}		

	public Long getLiczbaDni() {
		return liczbaDni;
	}

	public void setLiczbaDni(Long liczbaDni) {
		this.liczbaDni = liczbaDni;
	}

	public Float getWartosc() {
		return wartosc;
	}

	public void setWartosc(Float wartosc) {
		this.wartosc = wartosc;
	}		

	public String getPlatnosc() {
		return platnosc;
	}

	public void setPlatnosc(String platnosc) {
		this.platnosc = platnosc;
	}

	public void onLoad() throws IOException {
				  
		 loaded = (Pojazd) session.getAttribute("pojazd");
		 placowkaGet = Integer.parseInt((String)session.getAttribute("placowka"));
		 dataWyp = (Date) session.getAttribute("dataWyp");
		 dataZw = (Date) session.getAttribute("dataZw");
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
		 dataWypSt = formatter.format(dataWyp);
		 dataZwSt =  formatter.format(dataZw);
		 
		 
		 idKonta = (Integer) session.getAttribute("id");
		 		 
		 if (idKonta != null) { 						
			
			 klient = klientDAO.getClientInfo(idKonta);
		 
		 }
		 		 
		 if (dataWyp != null && dataZw != null) {
			
			LocalDateTime dateWypLoc = dataWyp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDateTime dateZwLoc = dataZw.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();				  		 		 
			liczbaDni = ChronoUnit.DAYS.between(dateWypLoc, dateZwLoc);
			if (liczbaDni == 0L) {
				liczbaDni = 1L;
			}
		 } 
		
		 if (loaded != null) {
			 pojazd = loaded;
			 wartosc = liczbaDni.floatValue() * (Float) pojazd.getCenaDoba();
		 }
		
		 if (placowkaGet != null) {
			
			 placowka = placowkaDAO.get(placowkaGet);
		 }
											
		 else {
			 ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));			
		 }		
		
	}
	
	public String doSave() {
		
		if (loaded == null || dataWyp == null || dataZw == null || klient == null || placowka == null || wartosc == null || platnosc == null) {
			
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieoczekiwany błąd", null));
			return PAGE_STAY_AT_THE_SAME;
		}
				
		//Save rent into the database
		 try {
			
			Wypozyczenie wypozyczenie = new Wypozyczenie();
			
			wypozyczenie.setKlient(klient);
			wypozyczenie.setPojazd(pojazd);
			wypozyczenie.setPlacowka(placowka);
			wypozyczenie.setDataWyp(dataWyp);
			wypozyczenie.setDataZw(dataZw);
			wypozyczenie.setWartWyp(wartosc);
			wypozyczenie.setFormaPlat(platnosc);
			wypozyczenie.setAktywny("tak");
			wypozyczenie.setUwagi("Brak");
			
			wypozyczenieDAO.insert(wypozyczenie);
			
			pojazd.setWypozyczony("tak");			
			pojazdDAO.update(pojazd);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		 
		 //remove rent attributes from session
		 session.removeAttribute("pojazd");
		 session.removeAttribute("placowka");
		 session.removeAttribute("dataWyp");
		 session.removeAttribute("dataZw");
		 		
		return PAGE_RENT_DONE;
	}
}
