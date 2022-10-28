package it.prova.gestioneordiniarticolicategorie.service;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.exception.ArticoliAncoraPresentiNellOrdineException;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineServiceImpl implements OrdineService {

	private OrdineDAO ordineDAOInstance;

	@Override
	public List<Ordine> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			ordineDAOInstance.setEntityManager(entityManager);
			return ordineDAOInstance.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Ordine findById(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			return ordineDAOInstance.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Ordine o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			ordineDAOInstance.update(o);
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
	public void inserisciNuovo(Ordine o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			ordineDAOInstance.insert(o);
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
	public void rimuovi(Ordine o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			if (!o.getArticoli().isEmpty())
				throw new ArticoliAncoraPresentiNellOrdineException(
						"impossibile eliminare ordine, contiene ancora articoli");
			ordineDAOInstance.delete(o);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	public void setDAOInstance(OrdineDAO dao) {
		ordineDAOInstance = dao;

	}

	@Override
	public void rimuoviArticoliDaOrdine(Ordine o) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			entityManager.getTransaction().begin();
			o.setArticoli(new HashSet<>());
			o = entityManager.merge(o);
			ordineDAOInstance.rimuoviArticoliDaOrdine(o);
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
	public Ordine findByIdEager(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ordineDAOInstance.setEntityManager(entityManager);
			return ordineDAOInstance.getEager(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
