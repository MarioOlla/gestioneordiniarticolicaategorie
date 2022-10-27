package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

public interface IBaseService<T> {
	public List<T> listAll() throws Exception;

	public T findById(Long id) throws Exception;

	public void aggiorna(T o) throws Exception;

	public void inserisciNuovo(T o) throws Exception;

	public void rimuovi(T o) throws Exception;

}
