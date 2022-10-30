package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();

	}

	@Override
	public Ordine get(Long id) throws Exception {
		if (id == null || id < 1)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		return entityManager.createQuery("from Ordine where id=?1", Ordine.class).setParameter(1, id).getResultStream()
				.findFirst().orElse(null);
	}

	@Override
	public void update(Ordine o) throws Exception {
		if (o == null)
			throw new Exception("Impossibile aggiornare voce nel DB. Input non valido.");
		o = entityManager.merge(o);

	}

	@Override
	public void insert(Ordine o) throws Exception {
		if (o == null)
			throw new Exception("Impossibile inserire voce nel DB. Input non valido.");
		entityManager.persist(o);
	}

	@Override
	public void delete(Ordine o) throws Exception {
		if (o == null)
			throw new Exception("Impossibile cancellare voce nel DB. Input non valido.");
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public void rimuoviArticoliDaOrdine(Ordine o) {
		entityManager.createQuery("delete from Articolo where ordine_id = ?1").setParameter(1, o.getId())
				.executeUpdate();

	}

	@Override
	public Ordine getEager(Long id) throws Exception {

		Ordine result = this.get(id);
		result.setArticoli(new HashSet<>(entityManager.createQuery("from Articolo where ordine_id=?1", Articolo.class)
				.setParameter(1, id).getResultList()));
		return result;
	}
	
	@Override
	public List<Ordine> getAllOrdiniConArticoliDiCategoria(Categoria categoria) throws Exception{
		if(categoria == null || categoria.getId() == null || categoria.getId() < 1)
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select o from Ordine o left join o.articoli a left join a.categorie c where c.id=?1" ,Ordine.class).setParameter(1, categoria.getId()).getResultList();
	}
	
	@Override
	public Ordine findOrdineSpedizionePiuRecenteConArticoliDiCategoria(Categoria categoria)throws Exception{
		if(categoria == null || categoria.getId() == null || categoria.getId() < 1)
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select o from Ordine o inner join o.articoli a inner join a.categorie c where c.id=?1 order by o.dataSpedizione desc", Ordine.class).setParameter(1, categoria.getId()).getResultStream().findFirst().orElse(null);
	}
	
	@Override
	public Long getPrezzoTotaleArticoliOrdiniPer(String destinatario)throws Exception {
		if(destinatario == null || destinatario.isBlank())
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select sum(a.prezzoSingolo) from Ordine o inner join o.articoli a where o.nomeDestinatario like ?1 ", Long.class).setParameter(1, destinatario).getResultStream().findFirst().orElse(null);
	}
	
	@Override
	public List<String> allIndirizziOrdiniArticoliConNumeroSerialeCome(String porzioneDelSeriale)throws Exception{
		if(porzioneDelSeriale == null || porzioneDelSeriale.isBlank())
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select o.indirizzoSpedizione from Ordine o inner join o.articoli a where a.numeroSeriale Like ?1",String.class).setParameter(1, "%"+porzioneDelSeriale+"%").getResultList();
	}

}
