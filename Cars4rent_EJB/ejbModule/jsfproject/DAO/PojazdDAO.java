package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	public List<Pojazd> getFullList() {
		List<Pojazd> list = null;

		Query query = em.createQuery("select p from Pojazd p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

}

