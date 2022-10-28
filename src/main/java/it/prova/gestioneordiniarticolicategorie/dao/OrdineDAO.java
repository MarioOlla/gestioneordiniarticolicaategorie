package it.prova.gestioneordiniarticolicategorie.dao;

import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	void rimuoviArticoliDaOrdine(Ordine o);

	Ordine getEager(Long id)throws Exception;

}
