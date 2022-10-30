package it.prova.gestioneordiniarticolicategorie.service;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService extends IBaseService<Categoria> {
	public void setDAOInstance(CategoriaDAO dao);

	public Categoria findByIdEager(Long id) throws Exception;

	public void aggiungiCategoriaAdArticolo(Categoria cat, Articolo art)throws Exception;

	public void rimuoviArticoliDaCategoria(Categoria o)throws Exception;

	public List<Categoria> tutteCategorieDistinteNellOrdine(Ordine ordine) throws Exception;

	public List<String> tuttiCodiciDiCategorieDegliOrdiniDelMeseDi(Date dataDiRiferimento) throws Exception;
}
