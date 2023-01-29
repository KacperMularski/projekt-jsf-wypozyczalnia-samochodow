package com.jsfproject.profile;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KlientDAO;
import jsfproject.DAO.KontoDAO;
import jsfproject.entities.Klient;
import jsfproject.entities.Konto;

@Named
@ViewScoped
public class ProfileEditBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	KlientDAO klientDAO;
	
	@EJB
	KontoDAO kontoDAO;
	
	private Klient klient;
	private Konto konto;
		
	private static final String PAGE_PROFILE_INFO = "profileInfo?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	FacesContext ctx = FacesContext.getCurrentInstance();

	//get client ID from session
	HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);	
	Integer id = (Integer) session.getAttribute("id");
	
	
	public Klient getKlient() {
		return klient;
	}
	
	public Konto getKonto() {
		return konto;
	}
	
	public void onLoad() throws IOException {
		
		klient = klientDAO.getClientInfo(id);
		
		if (klient == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nieoczekiwany błąd", null));				
		}
		
		konto = kontoDAO.get(id);
		
		if (konto == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nieoczekiwany błąd", null));				
		}
	}
			
	public String profileEdit() {
	
		try {
			 
			klientDAO.update(klient);
			kontoDAO.update(konto);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		
	return PAGE_PROFILE_INFO;
	
	}
	
	
	
}
