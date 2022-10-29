package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloService extends IBaseService<Articolo> {

	public void setDAOInstance(ArticoloDAO dao);
	
	public Articolo findByIdEager(Long id) throws Exception;

	public void aggiungiArticoloACategoria(Articolo articoloDaLinkare, Categoria categoriaDaLinkare)throws Exception;

	public void rimuoviCategorieDaArticolo(Articolo articolo) throws Exception;

	public Long sommaPrezzoArticoliDiCategoria(Categoria categoria) throws Exception;
}
