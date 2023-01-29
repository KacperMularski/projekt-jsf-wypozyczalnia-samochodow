package com.jsfproject.offerList;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jsfproject.DAO.PojazdDAO;
import jsfproject.entities.Pojazd;

@Named
@RequestScoped
public class OfferListBB {
	
	@EJB
	PojazdDAO pojazdDAO;
	
	private List<Pojazd> list = null;
	
	private LazyDataModel<Pojazd> lazyModelPojazd;
	
	FacesContext ctx = FacesContext.getCurrentInstance();	
		
	public List<Pojazd> getList() {
		return list;
	}
	public LazyDataModel<Pojazd> getLazyModelPojazd() {
		return lazyModelPojazd;
	}

	@PostConstruct
	public void init() {
							
	this.lazyModelPojazd = new LazyDataModel<Pojazd>() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public List<Pojazd> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
			
			setRowCount(pojazdDAO.getTotalResults());
			
			list = pojazdDAO.getListLazy(first, pageSize);
									
			if (list == null) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Brak wyników", null));				
			}
						
			return list;
		}

		
	};
	
	}
	 						
}
