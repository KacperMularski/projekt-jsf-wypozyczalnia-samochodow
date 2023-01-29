package com.jsfproject.adminEdit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jsfproject.DAO.KlientDAO;
import jsfproject.entities.Klient;

@Named
@ViewScoped
public class ClientListBB implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private String nazwisko;	
	private static final String PAGE_CLIENT_EDIT = "clientTableEdit?faces-redirect=true";	
	FacesContext ctx = FacesContext.getCurrentInstance();	
	
	
	List<Klient> list = null;
	Map<String,Object> searchParams = new HashMap<String, Object>();	
	private LazyDataModel<Klient> lazyModelKlient;
		
	@EJB
	KlientDAO klientDAO;
	
	public List<Klient> getList() {
		return list;
	}
	public LazyDataModel<Klient> getLazyModelKlient() {
		return lazyModelKlient;
	}
	
	
	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
		
	@PostConstruct
	public void init() {
		
	if (nazwisko != null && nazwisko.length() > 0){
		searchParams.put("nazwisko", nazwisko);
	}
	else {
		searchParams.clear();
	}
							
	this.lazyModelKlient = new LazyDataModel<Klient>() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public List<Klient> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
			
			setRowCount(klientDAO.getTotalResults(searchParams));						
			
			list = klientDAO.getListLazy(first, pageSize, searchParams);
									
			if (list == null) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Brak wyników", null));				
			}
						
			return list;
			}

		};
	}
	
	public String editPerson(Klient klient){
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		session.setAttribute("klient", klient);
		
		
		return PAGE_CLIENT_EDIT;
	}
		
}
