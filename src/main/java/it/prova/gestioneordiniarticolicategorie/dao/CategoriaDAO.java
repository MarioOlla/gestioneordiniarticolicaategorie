package it.prova.gestioneordiniarticolicategorie.dao;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	public void addCategoriaToArticolo(Categoria cat, Articolo art)throws Exception;
	public Categoria getEager(Long id) throws Exception;
	public void removeArticoliFromCategoria(Categoria o) throws Exception;
}
