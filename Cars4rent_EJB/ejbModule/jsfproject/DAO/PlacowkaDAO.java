package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Placowka; 

@Stateless

public class PlacowkaDAO {
	@PersistenceContext
	EntityManager em;
	
	public void insert(Placowka placowka) {
		em.persist(placowka);
	}

	public Placowka update(Placowka placowka) {
		return em.merge(placowka);
	}

	public void delete(Placowka placowka) {
		em.remove(em.merge(placowka));
	}

	public Placowka get(Object id) {
		return em.find(Placowka.class, id);
	}
	
	public List<Placowka> getFullList() {
		List<Placowka> list = null;

		Query query = em.createQuery("select p from Placowka p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
