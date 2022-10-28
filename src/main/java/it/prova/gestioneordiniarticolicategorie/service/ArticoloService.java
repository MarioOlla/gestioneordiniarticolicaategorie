package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloService extends IBaseService<Articolo> {

	public void setDAOInstance(ArticoloDAO dao);
	
	public Articolo findByIdEager(Long id) throws Exception;

	public void aggiungiArticoloACategoria(Articolo articoloDaLinkare, Categoria categoriaDaLinkare)throws Exception;

	void rimuoviCategorieDaArticolo(Articolo articolo) throws Exception;
}
