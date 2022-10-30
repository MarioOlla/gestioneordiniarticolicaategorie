package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();

	}

	@Override
	public Articolo get(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("Impossibile eseguire ricerca. Input non valido");
		return entityManager.createQuery("from Articolo where id=?1", Articolo.class).setParameter(1, id)
				.getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Articolo o) throws Exception {
		if (o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Articolo o) throws Exception {
		if (o == null)
			throw new Exception("Impossibile inserire nel DB. Input non valido.");
		entityManager.persist(o);

	}

	@Override
	public void delete(Articolo o) throws Exception {
		if (o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Articolo getEager(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("Impossibile eseguire ricerca. Input non valido");
		return entityManager.createQuery("from Articolo a left join fetch a.categorie where a.id=?1", Articolo.class)
				.setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

	@Override
	public Long totalePrezzoArticoliDiCategoria(Categoria categoria) throws Exception {
		if (categoria == null || categoria.getId() == null || categoria.getId() < 1)
			throw new Exception("Impossibile effettuare la ricerca, input non valido");
		return entityManager
				.createQuery("select sum(a.prezzoSingolo) from Categoria c left join c.articoli a where c.id=?1",
						Long.class)
				.setParameter(1, categoria.getId()).getResultStream().findFirst().orElse(null);
	}

	@Override
	public List<Articolo> allArticoliDiOrdiniConProblemi() throws Exception {
		return entityManager
				.createQuery("select a from Ordine o inner join o.articoli a where o.dataSpedizione > o.dataScadenza",
						Articolo.class)
				.getResultList();
	}
}
