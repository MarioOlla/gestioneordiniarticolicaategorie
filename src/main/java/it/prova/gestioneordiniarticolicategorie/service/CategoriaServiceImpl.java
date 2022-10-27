package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
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
		// TODO Auto-generated method stub

	}

	public void setDAOInstance(CategoriaDAO dao) {
		categoriaDAOInstance = dao;
	}

}
