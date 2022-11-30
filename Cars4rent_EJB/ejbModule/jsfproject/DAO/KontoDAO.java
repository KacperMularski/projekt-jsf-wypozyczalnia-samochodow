package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Konto; 

@Stateless

public class KontoDAO {
	@PersistenceContext
	EntityManager em;
	
	public void insert(Konto konto) {
		em.persist(konto);
	}

	public Konto update(Konto konto) {
		return em.merge(konto);
	}

	public void delete(Konto konto) {
		em.remove(em.merge(konto));
	}

	public Konto get(Object id) {
		return em.find(Konto.class, id);
	}
	
	public List<Konto> getFullList() {
		List<Konto> list = null;

		Query query = em.createQuery("select p from Konto p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
