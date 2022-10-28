package it.prova.gestioneordiniarticolicategorie.service;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.CategorieAncoraLegateAdArticoloException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloServiceImpl implements ArticoloService {

	private ArticoloDAO articoloDAOInstance;

	@Override
	public List<Articolo> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			articoloDAOInstance.setEntityManager(entityManager);
			return articoloDAOInstance.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public Articolo findById(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			articoloDAOInstance.setEntityManager(entityManager);
			return articoloDAOInstance.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Articolo o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			articoloDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			articoloDAOInstance.update(o);
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
	public void inserisciNuovo(Articolo o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			articoloDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			articoloDAOInstance.insert(o);
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
	public void rimuovi(Articolo o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			articoloDAOInstance.setEntityManager(entityManager);

			if (!o.getCategorie().isEmpty())
				throw new CategorieAncoraLegateAdArticoloException(
						"impossibile eliminare, ci sono ancora categorie associate");

			articoloDAOInstance.delete(o);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	public void setDAOInstance(ArticoloDAO dao) {
		articoloDAOInstance = dao;
	}

	@Override
	public Articolo findByIdEager(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			articoloDAOInstance.setEntityManager(entityManager);
			return articoloDAOInstance.getEager(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiungiArticoloACategoria(Articolo articoloDaLinkare, Categoria categoriaDaLinkare) throws Exception {
		if (articoloDaLinkare == null || categoriaDaLinkare == null)
			throw new Exception("impossibile collegare articolo e categoria, input non validi.");

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			articoloDAOInstance.setEntityManager(entityManager);

			articoloDaLinkare = entityManager.merge(articoloDaLinkare);
			categoriaDaLinkare = entityManager.merge(categoriaDaLinkare);

			articoloDaLinkare.addArticoloToCategoria(categoriaDaLinkare);

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
	public void rimuoviCategorieDaArticolo(Articolo articolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			articoloDAOInstance.setEntityManager(entityManager);

			articolo.setCategorie(new HashSet<>());
			articoloDAOInstance.update(articolo);
			articoloDAOInstance.delete(articolo);

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
