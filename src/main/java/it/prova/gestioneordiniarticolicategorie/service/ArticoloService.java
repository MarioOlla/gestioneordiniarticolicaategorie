package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public interface ArticoloService extends IBaseService<Articolo> {

	public void setDAOInstance(ArticoloDAO dao);
}
