package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	public void addCategoriaToArticolo(Categoria cat, Articolo art)throws Exception;
	public Categoria getEager(Long id) throws Exception;
	public void removeArticoliFromCategoria(Categoria o) throws Exception;
	public List<Categoria> allCategorieInOrdine(Ordine ordine) throws Exception;
}
