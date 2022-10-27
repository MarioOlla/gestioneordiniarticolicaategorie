package it.prova.gestioneordiniarticolicategorie.dao;

public class MyDaoFactory {
	
	private static ArticoloDAO articoloDAOInstance = null;
	private static CategoriaDAO categoriaDAOInstance = null;
	private static OrdineDAO ordineDAOInstance = null;

	public static ArticoloDAO getArticoloDAOInstance() {
		if(articoloDAOInstance == null)
			articoloDAOInstance = new ArticoloDAOImpl();
		return articoloDAOInstance;
	}
	
	public static CategoriaDAO getCategoriaDAOInstance() {
		if(categoriaDAOInstance == null)
			categoriaDAOInstance = new CategoriaDAOImpl();
		return categoriaDAOInstance;
	}
	
	public static OrdineDAO geOrdineDAOInstance() {
		if(ordineDAOInstance == null)
			
			ordineDAOInstance = new OrdineDAOImpl();
		return ordineDAOInstance;
	}
}
