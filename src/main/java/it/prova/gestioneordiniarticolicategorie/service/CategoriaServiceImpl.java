package it.prova.gestioneordiniarticolicategorie.service;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.ArticoliAncoraCollegatiACategoriaException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaServiceImpl implements CategoriaService {

	private CategoriaDAO categoriaDAOInstance;

	@Override
	public List<Categoria> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDAOInstance.setEntityManager(entityManager);
			return categoriaDAOInstance.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Categoria findById(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDAOInstance.setEntityManager(entityManager);
			return categoriaDAOInstance.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Categoria o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			categoriaDAOInstance.update(o);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void inserisciNuovo(Categoria o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			categoriaDAOInstance.insert(o);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void rimuovi(Categoria o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			categoriaDAOInstance.setEntityManager(entityManager);

			if (!o.getArticoli().isEmpty())
				throw new ArticoliAncoraCollegatiACategoriaException(
						"impossibile eliminare, ci sono ancora articoli associati");

			categoriaDAOInstance.delete(o);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	public void setDAOInstance(CategoriaDAO dao) {
		categoriaDAOInstance = dao;
	}

	public void aggiungiCategoriaAdArticolo(Categoria cat, Articolo art) throws Exception {
		if (cat == null || art == null)
			throw new Exception("Impossibile eseguire operazione, input non validi.");

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			categoriaDAOInstance.setEntityManager(entityManager);

			cat = entityManager.merge(cat);
			art = entityManager.merge(art);

			cat.addCategoriaToArticolo(art);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Categoria findByIdEager(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDAOInstance.setEntityManager(entityManager);
			return categoriaDAOInstance.getEager(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuoviArticoliDaCategoria(Categoria o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			categoriaDAOInstance.setEntityManager(entityManager);

			o.setArticoli(new HashSet<>());
			o = entityManager.merge(o);
			categoriaDAOInstance.removeArticoliFromCategoria(o);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

}
