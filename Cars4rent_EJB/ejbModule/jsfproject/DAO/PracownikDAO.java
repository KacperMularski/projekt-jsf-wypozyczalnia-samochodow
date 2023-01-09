package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Klient;
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
	
	public Pracownik getEmployeeInfo(Object placowka) {		
		return em.find(Pracownik.class, placowka);	
	}
}
