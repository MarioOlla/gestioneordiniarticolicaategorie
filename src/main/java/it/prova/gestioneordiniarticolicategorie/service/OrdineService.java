package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService extends IBaseService<Ordine> {
	public void setDAOInstance(OrdineDAO dao);

	public void rimuoviArticoliDaOrdine(Ordine o) throws Exception;

	public Ordine findByIdEager(Long id)throws Exception;

	public List<Ordine> tuttiOrdiniConArticoliDiCategoria(Categoria categoria) throws Exception;

	public Ordine ordinePiuRecenteConArticoliDiCategoria(Categoria categoria) throws Exception;
}
