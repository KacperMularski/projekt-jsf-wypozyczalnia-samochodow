package jsfproject.DAO;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Klient;
import jsfproject.entities.Konto;
import jsfproject.entities.Pracownik;

@Stateless

public class KlientDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void insert(Klient klient) {
		em.persist(klient);
	}

	public Klient update(Klient klient) {
		return em.merge(klient);
	}

	public void delete(Klient klient) {
		em.remove(em.merge(klient));
	}

	public Klient get(Object id) {
		return em.find(Klient.class, id);
	}
		
	public List<Klient> getFullList() {
		List<Klient> list = null;

		Query query = em.createQuery("select p from Klient p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Klient getClientInfo(Integer id) {	
		
		Query query = em.createQuery("SELECT u FROM Klient u WHERE konto.id like :id ");
        query.setParameter("id", id);
        

        try {
            return (Klient) query.getResultList().get(0);
        } catch (Exception e) {    }

        return null;	
	}
	
	public List<Klient> getListLazy(int first, int pageSize, Map<String, Object> searchParams) {
		List<Klient> list = null;

		String select = "select p ";
		String from = "from Klient p ";
		String where = "";
		String orderby = "order by nazwisko asc, imie";

		
		String nazwisko = (String) searchParams.get("nazwisko");
		if (nazwisko != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "nazwisko like :nazwisko ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (nazwisko != null) {
			query.setParameter("nazwisko", nazwisko+"%");
		}
		
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
	
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Integer getTotalResults(Map<String, Object> searchParams) {
		
		String nazwisko = (String) searchParams.get("nazwisko");
		
		String select = "select p ";
		String from = "from Klient p ";
		String where = "";
		
		if (nazwisko != null) {
			where += "where nazwisko like :nazwisko ";
		}
			
		Query query = em.createQuery(select + from + where);
		
		if (nazwisko != null) {
			query.setParameter("nazwisko", nazwisko+"%");
		}
       
        try {
            return (Integer) query.getResultList().size();
        } catch (Exception e) {    }

        return null;
	}
	
	
}
