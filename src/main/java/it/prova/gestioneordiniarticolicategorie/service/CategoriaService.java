package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService extends IBaseService<Categoria> {
	public void setDAOInstance(CategoriaDAO dao);
}
