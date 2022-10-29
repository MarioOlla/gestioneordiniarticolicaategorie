package it.prova.gestioneordiniarticolicategorie.dao;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo>{

	public Articolo getEager(Long id)throws Exception;

	public Long totalePrezzoArticoliDiCategoria(Categoria categoria) throws Exception;
}
