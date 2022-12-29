package com.jsfproject.offerList;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import jsfproject.DAO.PojazdDAO;
import jsfproject.entities.Pojazd;

@Named
@RequestScoped
public class OfferListBB {
			
	@EJB
	PojazdDAO pojazdDAO;
	
	private List<Pojazd> list = null;
	
	public List<Pojazd> getList() {
				
		FacesContext ctx = FacesContext.getCurrentInstance();	
		
		list = pojazdDAO.getFullList();
		
		if (list == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Brak wyników", null));				
		}
		
		return list;
		
		
	}
}
