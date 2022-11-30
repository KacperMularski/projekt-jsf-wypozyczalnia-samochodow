package jsfproject.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsfproject.entities.Wypozyczenie; 

@Stateless

public class WypozyczenieDAO {
	@PersistenceContext
	EntityManager em;
	
	public void insert(Wypozyczenie wypozyczenie) {
		em.persist(wypozyczenie);
	}

	public Wypozyczenie update(Wypozyczenie wypozyczenie) {
		return em.merge(wypozyczenie);
	}

	public void delete(Wypozyczenie wypozyczenie) {
		em.remove(em.merge(wypozyczenie));
	}

	public Wypozyczenie get(Object id) {
		return em.find(Wypozyczenie.class, id);
	}
	
	public List<Wypozyczenie> getFullList() {
		List<Wypozyczenie> list = null;

		Query query = em.createQuery("select p from Wypozyczenie p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
