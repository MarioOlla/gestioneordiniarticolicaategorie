package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.MyDaoFactory;

public class MyServiceFactory {
	
	private static ArticoloService articoloServiceInstance = null;
	private static CategoriaService categoriaServiceInstance = null;
	private static OrdineService ordineServiceInstance = null;
	
	public static ArticoloService getArticoloServiceInstance(){
		if(articoloServiceInstance == null)
			articoloServiceInstance = new ArticoloServiceImpl();
		
		 articoloServiceInstance.setDAOInstance(MyDaoFactory.getArticoloDAOInstance());
		 
		 return articoloServiceInstance;
	}
	
	public static CategoriaService getCategoriaServiceInstance() {
		if(categoriaServiceInstance == null)
			categoriaServiceInstance = new CategoriaServiceImpl();
		
		 categoriaServiceInstance.setDAOInstance(MyDaoFactory.getCategoriaDAOInstance());
		 
		 return categoriaServiceInstance;
	}
	
	public static OrdineService getOrdineServiceInstance() {
		if(ordineServiceInstance == null)
			ordineServiceInstance = new OrdineServiceImpl();
		
		ordineServiceInstance.setDAOInstance(MyDaoFactory.geOrdineDAOInstance());
		
		return ordineServiceInstance;
	}
	
	
//	public static AppService getAppServiceInstance() {
//		if(appServiceInstance == null)
//			appServiceInstance = new AppServiceImpl();
//		
//		appServiceInstance.setAppDAO(MyDaoFactory.getaAppDAOInstance());
//		
//		return appServiceInstance;
//
//	}

}
