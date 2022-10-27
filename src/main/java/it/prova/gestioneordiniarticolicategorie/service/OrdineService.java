package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService extends IBaseService<Ordine> {
	public void setDAOInstance(OrdineDAO dao);
}
