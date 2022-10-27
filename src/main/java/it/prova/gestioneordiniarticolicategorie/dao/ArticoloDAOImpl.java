package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public class ArticoloDAOImpl implements ArticoloDAO {
	
	private EntityManager entityManager;
	
	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
		 
	}

	@Override
	public Articolo get(Long id) throws Exception {
		if(id == null || id < 1)
			throw new Exception("Impossibile eseguire ricerca. Input non valido");
		return entityManager.createQuery("from Articolo where id=?1", Articolo.class).setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Articolo o) throws Exception {
		if(o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Articolo o) throws Exception {
		if(o == null )
			throw new Exception("Impossibile inserire nel DB. Input non valido.");
		entityManager.persist(o);

	}

	@Override
	public void delete(Articolo o) throws Exception {
		if(o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

}