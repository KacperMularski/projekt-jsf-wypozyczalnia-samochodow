package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Klient;

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
	
	
}
