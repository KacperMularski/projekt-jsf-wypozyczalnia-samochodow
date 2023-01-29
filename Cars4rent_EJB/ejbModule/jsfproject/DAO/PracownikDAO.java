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

public class PracownikDAO {
	@PersistenceContext
	EntityManager em;
	
	public void insert(Pracownik pracownik) {
		em.persist(pracownik);
	}

	public Pracownik update(Pracownik pracownik) {
		return em.merge(pracownik);
	}

	public void delete(Pracownik pracownik) {
		em.remove(em.merge(pracownik));
	}

	public Pracownik get(Object id) {
		return em.find(Pracownik.class, id);
	}
	
	public List<Pracownik> getFullList() {
		List<Pracownik> list = null;

		Query query = em.createQuery("select p from Pracownik p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Pracownik getEmployeeInfo(Integer id) {
		
		Query query = em.createQuery("SELECT u FROM Pracownik u WHERE konto.id like :id ");
        query.setParameter("id", id);
        

        try {
            return (Pracownik) query.getResultList().get(0);
        } catch (Exception e) {    }

        return null;
	}
	
	public List<Pracownik> getListLazy(int first, int pageSize, Map<String, Object> searchParams) {
		List<Pracownik> list = null;

		String select = "select p ";
		String from = "from Pracownik p ";
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
		String from = "from Pracownik p ";
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
