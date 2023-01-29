package com.jsfproject.profile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KlientDAO;
import jsfproject.entities.Klient;

import jsfproject.DAO.PracownikDAO;
import jsfproject.entities.Pracownik;

@Named
@RequestScoped
public class ProfileInfoBB {
	
@EJB
KlientDAO klientDAO;

@EJB
PracownikDAO pracownikDAO;


private Klient klient;
private Pracownik pracownik;


FacesContext ctx = FacesContext.getCurrentInstance();

//get client ID from session
HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);	
Integer id = (Integer) session.getAttribute("id");


public Klient getKlient() {
								
	klient = klientDAO.getClientInfo(id);
	
	if (klient == null) {
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Nieoczekiwany błąd", null));				
	}	
	
	return klient;
		
}

public Pracownik getPracownik() {
	
	pracownik = pracownikDAO.getEmployeeInfo(id);
	
	if (pracownik == null) {
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Nieoczekiwany błąd", null));				
	}
	return pracownik;
}

	
}
