package com.jsfproject.profile;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KlientDAO;
import jsfproject.DAO.WypozyczenieDAO;
import jsfproject.entities.Klient;
import jsfproject.entities.Wypozyczenie;

@Named
@RequestScoped
public class ProfileRentHistoryBB {
			
	@EJB
	WypozyczenieDAO wypozyczenieDAO;
	
	@EJB
	KlientDAO klientDAO;

	private List<Wypozyczenie> list = null;
	
	public List<Wypozyczenie> getList() {
	
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		//get client ID from session
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);	
		Integer id = (Integer) session.getAttribute("id");
		
		Klient klient = klientDAO.getClientInfo(id);
		
		if (klient == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nieoczekiwany błąd ", null));							
		}	
		
		list = wypozyczenieDAO.getFullListById(klient);
		
		if (list == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Brak wyników", null));							
		}
				
		return list;	
	}
	
}
