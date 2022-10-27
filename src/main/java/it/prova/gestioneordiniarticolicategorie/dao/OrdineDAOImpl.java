package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {
	
	EntityManager entityManager;
	
	@Override
	public List<Ordine> list() throws Exception {
		return  entityManager.createQuery("from Ordine", Ordine.class).getResultList();
		
	}

	@Override
	public Ordine get(Long id) throws Exception {
		if(id == null || id < 1)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		return entityManager.createQuery("from Ordine where id=?1",Ordine.class).setParameter(1, id) .getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Ordine o) throws Exception {
		if(o == null)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Ordine o) throws Exception {
		if(o == null)
			throw new Exception("Impossibile inserire voce nel DB. Input non valido.");
		entityManager.persist(o);
	}

	@Override
	public void delete(Ordine o) throws Exception {
		if(o == null)
			throw new Exception("Impossibile cancellare voce nel DB. Input non valido.");
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

}
