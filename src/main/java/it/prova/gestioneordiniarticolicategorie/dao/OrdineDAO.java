package it.prova.gestioneordiniarticolicategorie.dao;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	public void rimuoviArticoliDaOrdine(Ordine o);

	public Ordine getEager(Long id)throws Exception;
	
	public List<Ordine> getAllOrdiniConArticoliDiCategoria(Categoria categoria) throws Exception;

	public Ordine findOrdineSpedizionePiuRecenteConArticoliDiCategoria(Categoria categoria) throws Exception;

	public Long getPrezzoTotaleArticoliOrdiniPer(String destinatario) throws Exception;

	public List<String> allIndirizziOrdiniArticoliConNumeroSerialeCome(String porzioneDelSeriale) throws Exception;

}
