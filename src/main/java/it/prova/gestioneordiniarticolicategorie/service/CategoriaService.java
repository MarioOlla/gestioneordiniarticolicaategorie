package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService extends IBaseService<Categoria> {
	public void setDAOInstance(CategoriaDAO dao);

	public Categoria findByIdEager(Long id) throws Exception;

	public void aggiungiCategoriaAdArticolo(Categoria cat, Articolo art)throws Exception;

	public void rimuoviArticoliDaCategoria(Categoria o)throws Exception;
}
