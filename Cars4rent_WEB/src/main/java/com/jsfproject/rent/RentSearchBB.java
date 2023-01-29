package com.jsfproject.rent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.PojazdDAO;
import jsfproject.entities.Pojazd;
import jsfproject.DAO.PlacowkaDAO;

@Named
@RequestScoped
public class RentSearchBB {
	
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RENT_PAY = "rentPay.xhtml";	
	FacesContext ctx = FacesContext.getCurrentInstance();
	
	private String placowka;
	private Date dataWyp;
	private Date dataZw;
	private String cenaSort;
	
	private boolean automatSort;
	private boolean manualSort;
	private boolean benzynaSort;
	private boolean dieselSort;
	
	private boolean checkForm = false;
	
	@EJB
	PojazdDAO pojazdDAO;
	
	@EJB
	PlacowkaDAO placowkaDAO;
			
	public String getPlacowka() {
		return placowka;
	}

	public void setPlacowka(String placowka) {
		this.placowka = placowka;
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
		
	public String getCenaSort() {
		return cenaSort;
	}

	public void setCenaSort(String cenaSort) {
		this.cenaSort = cenaSort;
	}		

	public boolean isAutomatSort() {
		return automatSort;
	}

	public void setAutomatSort(boolean automatSort) {
		this.automatSort = automatSort;
	}

	public boolean isManualSort() {
		return manualSort;
	}

	public void setManualSort(boolean manualSort) {
		this.manualSort = manualSort;
	}

	public boolean isBenzynaSort() {
		return benzynaSort;
	}

	public void setBenzynaSort(boolean benzynaSort) {
		this.benzynaSort = benzynaSort;
	}

	public boolean isDieselSort() {
		return dieselSort;
	}

	public void setDieselSort(boolean dieselSort) {
		this.dieselSort = dieselSort;
	}
	
	public boolean isCheckForm() {
		return checkForm;
	}

	public void setCheckForm(boolean checkForm) {
		this.checkForm = checkForm;
	}

	public String validate() {
		
		if (!dataWyp.equals(null) || !dataZw.equals(null) ) {
					
		LocalDate today = LocalDate.now();
		LocalDate dateWypLoc = dataWyp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateZwLoc = dataZw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();			
		
		if (today.isAfter(dateWypLoc) || today.isAfter(dateZwLoc)){
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Data odbioru lub data zwrotu nie może być mniejsza niż data dzisiejsza", null));
		}		
		else if (dateWypLoc.isAfter(dateZwLoc)) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Data odbioru nie może być mniejsza niż data zwrotu", null));
		}		
		else {
			checkForm = true;
		}
			
		}
		return PAGE_STAY_AT_THE_SAME;
	}

	public List<Pojazd> getList() {
		
		this.validate();
		
		List<Pojazd> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (placowka != null && placowka.length() > 0) {
			int placowkaInt = Integer.parseInt(placowka);
						
			searchParams.put("placowka", placowkaInt);
		}
		
		if (cenaSort != null && cenaSort.length() > 0) {			
			searchParams.put("cenaDoba", cenaSort);
		}
		
		if (automatSort == true && manualSort == false) {
			searchParams.put("skrzynia", "automatyczna");
		}
		
		if (automatSort == false && manualSort == true) {
			searchParams.put("skrzynia", "manualna");
		}
		
		if (benzynaSort == true && dieselSort == false) {
			searchParams.put("rodzPaliwa", "benzyna");
		}
		
		if (benzynaSort == false && dieselSort == true) {
			searchParams.put("rodzPaliwa", "diesel");
		}
		
		list = pojazdDAO.getList(searchParams);
						
		return list;
		
	}
	
	public String doRent(Pojazd pojazd) {
			
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		session.setAttribute("pojazd", pojazd);
		session.setAttribute("dataWyp", dataWyp);
		session.setAttribute("dataZw", dataZw);
		session.setAttribute("placowka", placowka);
	
		return PAGE_RENT_PAY;
	}
						
}
