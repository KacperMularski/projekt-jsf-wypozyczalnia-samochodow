package com.jsfproject.adminEdit;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KontoDAO;
import jsfproject.DAO.PlacowkaDAO;
import jsfproject.DAO.PracownikDAO;
import jsfproject.entities.Pracownik;

@Named
@ViewScoped
public class EmployeeEditBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_EMPLOYEE_LIST = "employeeTableList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	FacesContext ctx = FacesContext.getCurrentInstance();
	
	private Pracownik pracownik = new Pracownik();
	private Pracownik loaded = null;
	
	@EJB
	PracownikDAO pracownikDAO;
	
	@EJB
	KontoDAO kontoDAO;
	
	@EJB
	PlacowkaDAO placowkaDAO;
	
	
	public Pracownik getPracownik() {
		return pracownik;
	}
	
	public void onLoad() throws IOException {
		
		 HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);	 
		 loaded = (Pracownik) session.getAttribute("pracownik");
		 
		if (loaded != null) {
			pracownik = loaded;
					
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
			if (pracownik.getIdPracownika() == 0) {
				// new record
				pracownikDAO.insert(pracownik);
			} else {
				// existing record
				pracownikDAO.update(pracownik);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_EMPLOYEE_LIST;
	}
}
