package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;

public class TestGestione {

	public static void main(String[] args) {
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();

		try {
			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testInserisciOrdine(ordineServiceInstance);
			
			testAggiornaOrdine(ordineServiceInstance);
			
			testInserisciArticolo(articoloServiceInstance, ordineServiceInstance);
			
			testAggiornaArticolo(articoloServiceInstance);
			
			testRimuoviArticoloSenzaCategoria(articoloServiceInstance);
			
			testInserisciCategoria(categoriaServiceInstance);
			
			testAggiornaCategoria(categoriaServiceInstance);
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserisciOrdine(OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testInserisciOrdine");
		Ordine nuovo = new Ordine("Loris", "viale Monte Rosa 16", null,
				new SimpleDateFormat("YYYY-MM-DD").parse("2022-12-31"));
		ordineService.inserisciNuovo(nuovo);
		if (ordineService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testInserisciOrdine : PASS");
	}
	
	private static void testAggiornaOrdine(OrdineService ordineService)throws Exception{
		System.out.println("\n.....inizio testAggiornaOrdine");
		List<Ordine> tuttiSuDB = ordineService.listAll();
		if(tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB. ");
		String vecchioDestinatario = tuttiSuDB.get(0).getNomeDestinatario();
		Ordine nuovo = new Ordine("Loris S", "viale Monte Rosa 16", null,
				new SimpleDateFormat("YYYY-MM-DD").parse("2022-12-31"));
		nuovo.setId(tuttiSuDB.get(0).getId());
		ordineService.aggiorna(nuovo);
		if (ordineService.findById(tuttiSuDB.get(0).getId()).getNomeDestinatario().equals(vecchioDestinatario))
			throw new RuntimeException("FAIL : aggiornamento non avvenuto.");
		System.out.println("..... fine testAggiornaOrdine : PASS");
	}
	
	private static void testInserisciArticolo(ArticoloService articoloService, OrdineService ordineService)throws Exception{
		System.out.println("\n.....inizio testInserisciArticolo");
		List<Ordine> tuttiSuDB = ordineService.listAll();
		if(tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB, impossibile inserire articolo. ");
		Articolo nuovo = new Articolo("hot dog", "8HD0D3H", 2, new Date());
		nuovo.setOrdine(tuttiSuDB.get(0));
		articoloService.inserisciNuovo(nuovo);
		if (articoloService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testInserisciArticolo : PASS");
	}
	
	private static void testAggiornaArticolo(ArticoloService articoloService)throws Exception{
		System.out.println("\n.....inizio testAggiornaArticolo");
		List<Articolo> tuttiSuDB = articoloService.listAll();
		if(tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono articoli su DB, impossibile aggiornare voce ");
		Articolo nuovo = new Articolo("wurstel", "8HD0D3H", 3, new Date());
		nuovo.setOrdine(tuttiSuDB.get(0).getOrdine());
		nuovo.setId(tuttiSuDB.get(0).getId());
		articoloService.aggiorna(nuovo);
		if (articoloService.findById(tuttiSuDB.get(0).getId()).getPrezzoSingolo() != 3)
			throw new RuntimeException("FAIL : aggiornamento voce non avvenuto.");
		System.out.println("..... fine testAggiornaArticolo : PASS");
	}
	
	private static void testRimuoviArticoloSenzaCategoria(ArticoloService articoloService)throws Exception{
		System.out.println("\n.....inizio testRimuoviArticoloSenzaCategoria");
		List<Articolo> tuttiSuDB = articoloService.listAll();
		if(tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono articoli su DB. ");
		articoloService.rimuovi(tuttiSuDB.get(0));
		if (!articoloService.listAll().isEmpty())
			throw new RuntimeException("FAIL : eliminazione voce non avvenuta.");
		System.out.println("..... fine testRimuoviArticoloSenzaCategoria : PASS");
	}
	
	private static void testInserisciCategoria(CategoriaService categoriaService)throws Exception{
		System.out.println("\n.....inizio testAggiungiCategoria");
		Categoria nuova = new Categoria("alimentare", "food");
		categoriaService.inserisciNuovo(nuova);
		if(categoriaService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testAggiungiCategoria : PASS");
	}
	
	private static void testAggiornaCategoria(CategoriaService  categoriaService)throws Exception{
		System.out.println("\n.....inizio testAggiornaCategoria");
		List<Categoria> tuttiSuDB = categoriaService.listAll();
		if(tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB. ");
		String vecchiaDescrizione = tuttiSuDB.get(0).getDescrizione();
		Categoria nuova = new Categoria("prodotti alimentari", "food");
		nuova.setId(tuttiSuDB.get(0).getId());
		categoriaService.aggiorna(nuova);
		if (categoriaService.findById(tuttiSuDB.get(0).getId()).getDescrizione().equals(vecchiaDescrizione))
			throw new RuntimeException("FAIL : aggiornamento non avvenuto.");
		System.out.println("..... fine testAggiornaCategoria : PASS");
	}
	
	
	
	private static void stampaContenutoDB(ArticoloService articoloService, CategoriaService categoriaService,
			OrdineService ordineService) throws Exception {
		System.out.println("Nel DB al momento ci sono " + articoloService.listAll().size() + " articoli, "
				+ categoriaService.listAll().size() + " categorie e " + ordineService.listAll().size() + " ordini.");
	}

}
