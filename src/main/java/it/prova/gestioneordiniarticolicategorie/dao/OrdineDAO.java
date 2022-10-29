package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	void rimuoviArticoliDaOrdine(Ordine o);

	Ordine getEager(Long id)throws Exception;
	
	public List<Ordine> getAllOrdiniConArticoliDiCategoria(Categoria categoria) throws Exception;

	Ordine findOrdineSpedizionePiuRecenteConArticoliDiCategoria(Categoria categoria) throws Exception;

}
