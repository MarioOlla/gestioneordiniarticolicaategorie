package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

	EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();

	}

	@Override
	public Categoria get(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("from Categoria where id=?1", Categoria.class).setParameter(1, id)
				.getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Categoria o) throws Exception {
		if (o == null || o.getId() == null || o.getId() < 1)
			throw new Exception("Impossibile aggiornare elemento, input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Categoria o) throws Exception {
		if (o == null)
			throw new Exception("Impossibile inserire elemento, input non valido.");
		entityManager.persist(o);

	}

	@Override
	public void delete(Categoria o) throws Exception {
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public void addCategoriaToArticolo(Categoria cat, Articolo art) throws Exception {
		if (cat == null || art == null)
			throw new Exception("Inmpossibile collegare categoria ed articolo, uno o entrambi gli input non validi");
		cat.addCategoriaToArticolo(art);
		cat = entityManager.merge(cat);
		art = entityManager.merge(art);
	}

	@Override
	public Categoria getEager(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("from Categoria c left join fetch c.articoli where c.id=?1", Categoria.class)
				.setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

	@Override
	public void removeArticoliFromCategoria(Categoria o) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where categoria_id=?1")
				.setParameter(1, o.getId()).executeUpdate();

	}
	
	@Override
	public List<Categoria> allCategorieInOrdine(Ordine ordine) throws Exception{
		if(ordine == null || ordine.getId() == null || ordine .getId() < 1)
			throw new Exception("Impossibile effettuare ricerca, input non valido.");
		
		return entityManager.createQuery("select distinct c from Categoria c inner join c.articoli a where a.ordine.id=?1",Categoria.class).setParameter(1, ordine.getId()).getResultList();
	}
	
	@Override
	public List<String> allCodiciDiCategorieDegliOrdiniEffettuatiNelMeseDi(Date dataDiRiferimento)throws Exception{
		return entityManager.createQuery("select distinct c.codice from Ordine o inner join o.articoli a inner join a.categorie c where month(o.dataSpedizione)=month(?1) and year(o.dataSpedizione)=year(?1)", String.class).setParameter(1,new java.sql.Date(dataDiRiferimento.getTime())).getResultList();
	}
}
