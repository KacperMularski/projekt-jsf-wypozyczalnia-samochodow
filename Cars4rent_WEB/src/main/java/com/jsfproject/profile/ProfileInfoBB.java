package com.jsfproject.profile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KontoDAO;
import jsfproject.entities.Konto;

import jsfproject.DAO.KlientDAO;
import jsfproject.entities.Klient;

import jsfproject.DAO.PracownikDAO;
import jsfproject.entities.Pracownik;

import java.rmi.Remote;
import java.util.Enumeration;

import javax.faces.simplesecurity.RemoteClient;

@Named
@RequestScoped
public class ProfileInfoBB {
	
@EJB
KlientDAO klientDAO;

@EJB
KontoDAO kontoDAO;

@EJB
PracownikDAO pracownikDAO;

private Klient klient;
private Pracownik pracownik;

FacesContext ctx = FacesContext.getCurrentInstance();

//get client ID from session
HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);	
Integer id = (Integer) session.getAttribute("id");


public Klient getKlient() {
								
	Klient klient = klientDAO.getClientInfo(id);
	
	if (klient == null) {
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Niepoprawny login lub hasło", null));				
	}	
	
	return klient;
		
}

public Pracownik getPracownik() {
			
	Pracownik pracownik = pracownikDAO.getEmployeeInfo(2);
	
	if (pracownik == null) {
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Niepoprawny login lub hasło", null));				
	}	
	
	System.out.println(pracownik.getImie());
	
	return pracownik;
		
}
	
}
