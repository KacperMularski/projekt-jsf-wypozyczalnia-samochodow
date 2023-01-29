package com.jsfproject.adminEdit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jsfproject.DAO.PracownikDAO;
import jsfproject.entities.Pracownik;


@Named
@ViewScoped
public class EmployeeListBB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nazwisko;	
	private static final String PAGE_EMPLOYEE_EDIT = "employeeTableEdit?faces-redirect=true";	
	FacesContext ctx = FacesContext.getCurrentInstance();	
	
	List<Pracownik> list = null;
	Map<String,Object> searchParams = new HashMap<String, Object>();	
	private LazyDataModel<Pracownik> lazyModelPracownik;
	
	@EJB
	PracownikDAO pracownikDAO;
	
	public List<Pracownik> getList() {
		return list;
	}
	public LazyDataModel<Pracownik> getLazyModelPracownik() {
		return lazyModelPracownik;
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
						
		this.lazyModelPracownik = new LazyDataModel<Pracownik>() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public List<Pracownik> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
			
			setRowCount(pracownikDAO.getTotalResults(searchParams));
					
			list = pracownikDAO.getListLazy(first, pageSize, searchParams);
									
			if (list == null) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Brak wyników", null));				
			}
						
			return list;
			}

		};
	}
			
	public String editEmployee(Pracownik pracownik) {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		session.setAttribute("pracownik", pracownik);
		
		return PAGE_EMPLOYEE_EDIT;
	}
	
}
