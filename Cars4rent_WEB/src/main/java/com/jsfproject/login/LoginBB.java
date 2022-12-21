package com.jsfproject.login;

import java.rmi.Remote;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jsfproject.DAO.KontoDAO;
import jsfproject.entities.Konto;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_MAIN = "/public/index?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String haslo;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	@EJB
	KontoDAO kontoDAO;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get User from "database"
		Konto konto = kontoDAO.getLoginData(login, haslo);

		// 2. if bad login or password - stay with error info
		if (konto == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session
		
		RemoteClient<Konto> client = new RemoteClient<Konto>(); //create new RemoteClient
		client.setDetails(konto);			
		client.getRoles().add(konto.getRola());
			
	
		//store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		// and enter the system (now SecurityFilter will pass the request)
		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_MAIN;
	}
	
}
