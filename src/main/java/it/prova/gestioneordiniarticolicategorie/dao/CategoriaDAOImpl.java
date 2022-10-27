package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {
	
	EntityManager entityManager;
	
	@Override
	public List<Categoria> list() throws Exception {
		return  entityManager.createQuery("from Categoria", Categoria.class).getResultList();
		
	}

	@Override
	public Categoria get(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("from Categoria where id=?1", Categoria.class).setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Categoria o) throws Exception {
		if(o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare elemento, input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Categoria o) throws Exception {
		if(o == null)
			throw new Exception("Impossibile inserire elemento, input non valido.");
		entityManager.persist(o);

	}

	@Override
	public void delete(Categoria o) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

}
