package com.jsfproject.profile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KontoDAO;
import jsfproject.entities.Konto;

@Named
@RequestScoped
public class ProfilePassEditBB {
	
	@EJB
	KontoDAO kontoDAO;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PROFILE = "/pages/profile/profileInfo";
	
	private String passOld;
	private String passNew;
	private String passNewRe;
	
	public String getPassOld() {
		return passOld;
	}
	public void setPassOld(String passOld) {
		this.passOld = passOld;
	}
	public String getPassNew() {
		return passNew;
	}
	public void setPassNew(String passNew) {
		this.passNew = passNew;
	}
	public String getPassNewRe() {
		return passNewRe;
	}
	public void setPassNewRe(String passNewRe) {
		this.passNewRe = passNewRe;
	}
	
	FacesContext ctx = FacesContext.getCurrentInstance();

	//get client ID from session
	HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);	
	Integer id = (Integer) session.getAttribute("id");
	
	public String doPasswordEdit() {
		
		Konto konto = kontoDAO.checkPasswordEdit(id, passOld);
		
		if (konto == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Błędne hasło", null));
			return PAGE_STAY_AT_THE_SAME;
			
		}
		
		//check password repeat
		if (! passNew.equals(passNewRe)) {
					
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Hasła muszą być identyczne", null));								
			return PAGE_STAY_AT_THE_SAME;
					
		}
		
		konto.setHaslo(passNew);
		kontoDAO.update(konto);
		
		return PAGE_PROFILE;
	}
	
	
	
}
