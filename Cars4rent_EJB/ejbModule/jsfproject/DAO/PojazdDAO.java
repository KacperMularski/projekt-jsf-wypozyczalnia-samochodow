package jsfproject.DAO;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Klient;
import jsfproject.entities.Konto;
import jsfproject.entities.Pojazd; 

@Stateless

public class PojazdDAO {
	@PersistenceContext
	EntityManager em;
	
	public void insert(Pojazd pojazd) {
		em.persist(pojazd);
	}

	public Pojazd update(Pojazd pojazd) {
		return em.merge(pojazd);
	}

	public void delete(Pojazd pojazd) {
		em.remove(em.merge(pojazd));
	}

	public Pojazd get(Object id) {
		return em.find(Pojazd.class, id);
	}
	
	public List<Pojazd> getListLazy(int first, int pageSize) {
		List<Pojazd> list = null;

		Query query = em.createQuery("select p from Pojazd p");
		
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Integer getTotalResults() {
		
		Query query = em.createQuery("select p from Pojazd p");
       
        try {
            return (Integer) query.getResultList().size();
        } catch (Exception e) {    }

        return null;
	}
	
	public List<Pojazd> getList(Map<String, Object> searchParams) {
		List<Pojazd> list = null;
		
		String cenaDoba = (String) searchParams.get("cenaDoba");
		String orderby = null; 
		
		String select = "select p ";
		String from = "from Pojazd p ";
		String where = "";
		if (cenaDoba != null) {
			if (cenaDoba.equals("ASC")) {
			orderby = "order by cenaDoba ASC";
			}
			if (cenaDoba.equals("DESC")) {
			orderby = "order by cenaDoba DESC";	
			}
		}
		else {
			orderby = "order by marka desc";
		}				
		
		Integer placowka = (Integer) searchParams.get("placowka");
		if (placowka != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "placowka.id like :placowka ";
		}
		
		String skrzynia = (String) searchParams.get("skrzynia");
		if (skrzynia != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "skrzynia like :skrzynia ";
		}
		String rodzPaliwa = (String) searchParams.get("rodzPaliwa");
		if (rodzPaliwa != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "rodzPaliwa like :rodzPaliwa ";
		}
		//czy wypozyczony
		if (where.isEmpty()) {
			where = "where ";
		} else {
			where += "and ";
		}		
		where += "wypozyczony = 'nie' ";
		
		Query query = em.createQuery(select + from + where + orderby);
		
		
		
		if (skrzynia != null) {
			query.setParameter("skrzynia", skrzynia+"%");
		}
		
		if (rodzPaliwa != null) {
			query.setParameter("rodzPaliwa", rodzPaliwa+"%");
		}
		
		if (placowka != null) {
			query.setParameter("placowka", placowka);
		}	
	
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

}

