package com.jsfproject.adminEdit;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import jsfproject.DAO.KlientDAO;
import jsfproject.entities.Klient;

@Named
@ViewScoped
public class ClientEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CLIENT_LIST = "clientTableList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	FacesContext ctx = FacesContext.getCurrentInstance();

	private Klient klient = new Klient();
	private Klient loaded = null;
	
	@EJB
	KlientDAO klientDAO;

	public Klient getKlient() {
		return klient;
	}
	
	public void onLoad() throws IOException {
		
		 HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);	 
		 loaded = (Klient) session.getAttribute("klient");
		 		
		if (loaded != null) {
			klient = loaded;
						
		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			
		}

	}
	
	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (klient.getIdKlienta() == 0) {
				// new record
				klientDAO.insert(klient);
			} else {
				// existing record
				klientDAO.update(klient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_CLIENT_LIST;
	}
	
	
}
