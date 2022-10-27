package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;

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
			articoloDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
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

}
