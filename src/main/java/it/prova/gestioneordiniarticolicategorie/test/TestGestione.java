package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.ArticoliAncoraCollegatiACategoriaException;
import it.prova.gestioneordiniarticolicategorie.exception.ArticoliAncoraPresentiNellOrdineException;
import it.prova.gestioneordiniarticolicategorie.exception.CategorieAncoraLegateAdArticoloException;
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

			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testAggiungiCategoriaAdArticolo(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);

			testAggiungiArticoloACategoria(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			
			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			
			testTuttiOrdiniConArticoliDiCategoria(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			
			testTutteCategorieDistinteNellOrdine(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			
			testSommaPrezzoArticoliDiCategoria(categoriaServiceInstance, articoloServiceInstance);
			
			testOrdinePiuRecenteConArticoliDiCategoria(categoriaServiceInstance, ordineServiceInstance);
			
			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testRimuoviArticolo(articoloServiceInstance);

			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testRimuoviCategoria(categoriaServiceInstance);

			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testRimuoviOrdine(ordineServiceInstance);

			stampaContenutoDB(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserisciOrdine(OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testInserisciOrdine");
		Ordine nuovo = new Ordine("Loris", "viale Monte Rosa 16", new Date(),
				new SimpleDateFormat("YYYY-MM-DD").parse("2022-12-31"));
		ordineService.inserisciNuovo(nuovo);
		if (ordineService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testInserisciOrdine : PASS");
	}

	private static void testAggiornaOrdine(OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testAggiornaOrdine");
		List<Ordine> tuttiSuDB = ordineService.listAll();
		if (tuttiSuDB.isEmpty())
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

	private static void testInserisciArticolo(ArticoloService articoloService, OrdineService ordineService)
			throws Exception {
		System.out.println("\n.....inizio testInserisciArticolo");
		List<Ordine> tuttiSuDB = ordineService.listAll();
		if (tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB, impossibile inserire articolo. ");
		Articolo nuovo = new Articolo("hot dog", "8HD0D3H", 2, new Date());
		nuovo.setOrdine(tuttiSuDB.get(0));
		articoloService.inserisciNuovo(nuovo);
		if (articoloService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testInserisciArticolo : PASS");
	}

	private static void testAggiornaArticolo(ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testAggiornaArticolo");
		List<Articolo> tuttiSuDB = articoloService.listAll();
		if (tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono articoli su DB, impossibile aggiornare voce ");
		Articolo nuovo = new Articolo("wurstel", "8HD0D3H", 3, new Date());
		nuovo.setOrdine(tuttiSuDB.get(0).getOrdine());
		nuovo.setId(tuttiSuDB.get(0).getId());
		articoloService.aggiorna(nuovo);
		if (articoloService.findById(tuttiSuDB.get(0).getId()).getPrezzoSingolo() != 3)
			throw new RuntimeException("FAIL : aggiornamento voce non avvenuto.");
		System.out.println("..... fine testAggiornaArticolo : PASS");
	}

	private static void testRimuoviArticoloSenzaCategoria(ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testRimuoviArticoloSenzaCategoria");
		List<Articolo> tuttiSuDB = articoloService.listAll();
		if (tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono articoli su DB. ");
		Articolo daCancellare = articoloService.findByIdEager(tuttiSuDB.get(0).getId());
		articoloService.rimuovi(daCancellare);
		if (!articoloService.listAll().isEmpty())
			throw new RuntimeException("FAIL : eliminazione voce non avvenuta.");
		System.out.println("..... fine testRimuoviArticoloSenzaCategoria : PASS");
	}

	private static void testInserisciCategoria(CategoriaService categoriaService) throws Exception {
		System.out.println("\n.....inizio testAggiungiCategoria");
		Categoria nuova = new Categoria("alimentare", "food");
		categoriaService.inserisciNuovo(nuova);
		if (categoriaService.listAll().isEmpty())
			throw new RuntimeException("FAIL : inserimento non avvenuto.");
		System.out.println("..... fine testAggiungiCategoria : PASS");
	}

	private static void testAggiornaCategoria(CategoriaService categoriaService) throws Exception {
		System.out.println("\n.....inizio testAggiornaCategoria");
		List<Categoria> tuttiSuDB = categoriaService.listAll();
		if (tuttiSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB. ");
		String vecchiaDescrizione = tuttiSuDB.get(0).getDescrizione();
		Categoria nuova = new Categoria("prodotti alimentari", "food");
		nuova.setId(tuttiSuDB.get(0).getId());
		categoriaService.aggiorna(nuova);
		if (categoriaService.findById(tuttiSuDB.get(0).getId()).getDescrizione().equals(vecchiaDescrizione))
			throw new RuntimeException("FAIL : aggiornamento non avvenuto.");
		System.out.println("..... fine testAggiornaCategoria : PASS");
	}

	private static void testAggiungiCategoriaAdArticolo(OrdineService ordineService, CategoriaService categoriaService,
			ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testAggiungiCategoriaAdArticolo");
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if (tuttiOrdiniSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB, impossibile inserire articolo. ");
		Articolo nuovo = new Articolo("hot dog", "8HD0D3H", 2, new Date());
		nuovo.setOrdine(tuttiOrdiniSuDB.get(0));
		articoloService.inserisciNuovo(nuovo);
		List<Articolo> tuttiArticoliInDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		if (tuttiArticoliInDB.size() < 1 || tutteCategorieSuDB.size() < 1)
			throw new RuntimeException("FAIL : una o entrambe le tabelle sono vuote.");
		Articolo articoloDaLinkare = articoloService.findByIdEager(tuttiArticoliInDB.get(0).getId());
		Categoria categoriaDaLinkare = categoriaService.findByIdEager(tutteCategorieSuDB.get(0).getId());
		categoriaService.aggiungiCategoriaAdArticolo(categoriaDaLinkare, articoloDaLinkare);
		articoloDaLinkare = articoloService.findByIdEager(tuttiArticoliInDB.get(0).getId());
		if (articoloDaLinkare.getCategorie().isEmpty())
			throw new RuntimeException("FAIL : la categoria non e' stata aggiunta all' articolo.");
		System.out.println("..... fine testAggiungiCategoriaAdArticolo : PASS");
	}

	private static void testAggiungiArticoloACategoria(OrdineService ordineService, CategoriaService categoriaService,
			ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testAggiungiCategoriaAdArticolo");
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if (tuttiOrdiniSuDB.isEmpty())
			throw new RuntimeException("FAIL : non ci sono ordini DB, impossibile inserire articolo. ");
		Articolo nuovo = new Articolo("pizza", "8SJYY3B", 7, new Date());
		nuovo.setOrdine(tuttiOrdiniSuDB.get(0));
		articoloService.inserisciNuovo(nuovo);
		List<Articolo> tuttiArticoliInDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		if (tuttiArticoliInDB.size() < 1 || tutteCategorieSuDB.size() < 1)
			throw new RuntimeException("FAIL : una o entrambe le tabelle sono vuote.");
		Articolo articoloDaLinkare = articoloService
				.findByIdEager(tuttiArticoliInDB.get(tuttiArticoliInDB.size() - 1).getId());
		Categoria categoriaDaLinkare = categoriaService.findByIdEager(tutteCategorieSuDB.get(0).getId());
		articoloService.aggiungiArticoloACategoria(articoloDaLinkare, categoriaDaLinkare);
		articoloDaLinkare = articoloService.findByIdEager(tuttiArticoliInDB.get(0).getId());
		if (articoloDaLinkare.getCategorie().isEmpty())
			throw new RuntimeException("FAIL : la categoria non e' stata aggiunta all' articolo.");
		System.out.println("..... fine testAggiungiCategoriaAdArticolo : PASS");
	}

	private static void testRimuoviArticolo(ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testRimuoviArticolo");
		int quantiArticoli = articoloService.listAll().size();
		if (quantiArticoli < 1)
			throw new RuntimeException("FAIL : non ci sono articoli nel DB.");
		Articolo daEliminare = articoloService.findByIdEager(articoloService.listAll().get(0).getId());
		try {
			System.out.println("provo ad eliminare un articolo ancora collegato a una categoria");
			articoloService.rimuovi(daEliminare);
		} catch (CategorieAncoraLegateAdArticoloException e) {

		}
		System.out.println("provo a rimuovere la categoria e poi ad eliminare di nuovo l'articolo");
		articoloService.rimuoviCategorieDaArticolo(daEliminare);
		articoloService.rimuovi(daEliminare);
		System.out.println("..... fine testRimuoviArticolo : PASS");
	}

	private static void testRimuoviCategoria(CategoriaService categoriaService) throws Exception {
		System.out.println("\n.....inizio testRimuoviCategoria");
		int quanteCategorie = categoriaService.listAll().size();
		if (quanteCategorie < 1)
			throw new RuntimeException("FAIL : non ci sono categorie nel DB.");
		Categoria daEliminare = categoriaService.findByIdEager(categoriaService.listAll().get(0).getId());
		try {
			System.out.println("provo ad eliminare una categoria ancora collegata ad un articolo");
			categoriaService.rimuovi(daEliminare);
		} catch (ArticoliAncoraCollegatiACategoriaException e) {

		}
		System.out.println("provo a rimuovere l'articolo e poi ad eliminare di nuovo la categoria");
		categoriaService.rimuoviArticoliDaCategoria(daEliminare);
		categoriaService.rimuovi(daEliminare);
		System.out.println("..... fine testRimuoviCategoria : PASS");
	}

	private static void testRimuoviOrdine(OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testRimuoviOrdine");
		int quantiOrdini = ordineService.listAll().size();
		if (quantiOrdini < 1)
			throw new RuntimeException("FAIL : non ci sono ordini nel DB.");
		Ordine daEliminare = ordineService.findByIdEager(ordineService.listAll().get(0).getId());
		try {
			System.out.println("provo ad eliminare una categoria ancora collegata ad un articolo");
			ordineService.rimuovi(daEliminare);
		} catch (ArticoliAncoraPresentiNellOrdineException e) {

		}
		System.out.println("provo a rimuovere l'articolo e poi ad eliminare di nuovo la categoria");
		ordineService.rimuoviArticoliDaOrdine(daEliminare);
		ordineService.rimuovi(daEliminare);
		System.out.println("..... fine testRimuoviOrdine : PASS");
	}
	
	private static void testTuttiOrdiniConArticoliDiCategoria(OrdineService ordineService, CategoriaService categoriaService, ArticoloService articoloService)throws Exception{
		System.out.println("\n.....inizio testtuttiOrdiniConArticoliDiCategoria");
		List<Articolo> tuttiArticoliSuDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if(tuttiOrdiniSuDB.isEmpty() || tutteCategorieSuDB.isEmpty() || tuttiArticoliSuDB.isEmpty())
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		List<Ordine> result = ordineService.tuttiOrdiniConArticoliDiCategoria(tutteCategorieSuDB.get(0));
		if(result.isEmpty())
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testtuttiOrdiniConArticoliDiCategoria : PASS");
	}
	
	private static void testTutteCategorieDistinteNellOrdine(OrdineService ordineService, CategoriaService categoriaService, ArticoloService articoloService)throws Exception{
		System.out.println("\n.....inizio testTutteCategorieDistinteNellOrdine");
		List<Articolo> tuttiArticoliSuDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if(tuttiOrdiniSuDB.isEmpty() || tutteCategorieSuDB.isEmpty() || tuttiArticoliSuDB.isEmpty())
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		List<Categoria> result = categoriaService.tutteCategorieDistinteNellOrdine(tuttiOrdiniSuDB.get(0));
		if(result.size() != 1)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testTutteCategorieDistinteNellOrdine : PASS");
	}
	
	private static void testSommaPrezzoArticoliDiCategoria(CategoriaService categoriaService, ArticoloService articoloService) throws Exception{
		System.out.println("\n.....inizio testSommaPrezzoArticoliDiCategoria");
		List<Articolo> tuttiArticoliSuDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		if( tutteCategorieSuDB.isEmpty() || tuttiArticoliSuDB.isEmpty())
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		Long result = articoloService.sommaPrezzoArticoliDiCategoria(tutteCategorieSuDB.get(0));
		if(result != 9)
			throw new RuntimeException("FAIL : la somma dei prezzi non e' corretta.");
		System.out.println("..... fine testSommaPrezzoArticoliDiCategoria : PASS");
	}
	
	private static void testOrdinePiuRecenteConArticoliDiCategoria(CategoriaService categoriaService, OrdineService ordineService)throws Exception{
		System.out.println("\n.....inizio testOrdinePiuRecenteConArticoliDiCategoria");
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if(tuttiOrdiniSuDB.isEmpty() || tutteCategorieSuDB.isEmpty() )
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		Ordine result = ordineService.ordinePiuRecenteConArticoliDiCategoria(tutteCategorieSuDB.get(0));
		if(result==null)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testOrdinePiuRecenteConArticoliDiCategoria : PASS");
	}

	private static void stampaContenutoDB(ArticoloService articoloService, CategoriaService categoriaService,
			OrdineService ordineService) throws Exception {
		System.out.println("Nel DB al momento ci sono " + articoloService.listAll().size() + " articoli, "
				+ categoriaService.listAll().size() + " categorie e " + ordineService.listAll().size() + " ordini.");
	}

}
