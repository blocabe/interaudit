package tests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.AbstractTransactionalSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.domain.model.Task;
import com.interaudit.service.ITaskService;

public class TaskTestNG extends AbstractTransactionalSpringContextTests{
	
	static {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		}
	
	protected String[] getConfigLocations() {
		return new String[] {
				"applicationContext.xml" };
	}

	// couche service
	private ITaskService service;

	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (ITaskService) ctx.getBean("taskService");
		/*
		// on affiche
		dump();
		
		
		// on vide la base
		clean();
		
		// on la remplit
		fill();
		
		
		// on affiche
		dump();
		*/
		try
		{
			loadFromFileData();
		}catch(Exception e){
			
		}
		
		
	}

	@AfterClass
	public void terminate() {
		/*
		// affichage tables
		System.out.println("--------------- contenu de la base");
		dumpCategories();
		dumpArticles();
		System.out.println("-----------------------------------");
		*/
	}

	// logs
	private void log(String message) {
		System.out.println("----------- " + message);
	}
	
	public List<String> scanData(String line, String delimiter)
	{
		List<String> result = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer(line);
		int begin = 0 ;
		int end = 0;
		int position = 0;
		int lenght = buffer.length();
		while (end < lenght)
		{
			position = buffer.indexOf(delimiter, begin);
			if (position != -1)
			{
				end = position;
			}
			else
			{
				end = lenght;
			}
			
			result.add(buffer.subSequence(begin, end).toString());
			begin = position +1;
		}
		return result;
	}
	
	public void loadFromFileData() throws Exception{
		InputStream is = null;
		java.io.LineNumberReader lineReader = null;
		try
		{
			//Open the file dat within a stream
			String fileDataPath = "E:/dev/workspace_interaudit/InterAuditManagementSystem/docs/docs/tasks.csv" ;        		
			is = new java.io.FileInputStream(fileDataPath);
			//is = new java.io.FileInputStream("E:/DPS/dps_4million_ext");
			
		   lineReader = new java.io.LineNumberReader(new InputStreamReader(is));
		   int count = 0;
		   String currentLine = lineReader.readLine();
		   while(currentLine != null){
			   
			   List<String> tokens = scanData(currentLine, ";");
			   
			   String name = tokens.get(1);
			   String description = tokens.get(2); 
			   String codePrestation = tokens.get(3);
			   boolean chargeable = tokens.get(4).equalsIgnoreCase("1");
			   String code = tokens.get(5);
			   
			   Task task = new Task( name,  description,  codePrestation,
						 chargeable,code,false);
			   
			   service.saveOne(task);
			   System.out.println(++count);		
			   currentLine = lineReader.readLine();
		   }
																 			
			this.setComplete();
		}
		catch (Exception e)
		{
			return ;
		}
		finally
		{
			
			if (lineReader != null)
			{
				try
				{
					lineReader.close();
				}
				catch (Exception e)
				{
				}
			}
			
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	// remplissage tables
	public void fill() throws ParseException {
		
		// créer trois societes
		/*
		 Task(String name, String description, String codePrestation,
			boolean chargeable)
		 */
		Task[] taskArray = {/*
							new Task("Planning-budget", "Planning-budget", "4000",true),
							new Task("Preparation des confirmations", "banques , avocats, cadastre", "4001",true),
							new Task("Preparation lancement du dossier (DA,LS)", "Preparation lancement du dossier (DA,LS)", "4002",true),
							new Task("Dossier administratif", "Dossier administratif", "4003",true),
							new Task("Compte annuels", "controle: report,sommes pointage B/G", "4004",true),
							new Task("Prise de connaissance du dossier - repartition du travail", "Prise de connaissance du dossier - repartition du travail", "4005",true),
							new Task("Suivi du dossier chez le client/Supervision", "Suivi du dossier chez le client/Supervision", "4006",true),
							new Task("Frais d'établissement", "Frais d'établissement", "4007",true),
							new Task("Immobilisations incorporelles", "Immobilisations incorporelles", "4008",true),
							new Task("Immobilisations corporelles", "Immobilisations corporelles", "4009",true),
							new Task("Immobilisations financieres", "Immobilisations financieres", "4010",true),
							new Task("Stock-Inventaire physique", "Stock-Inventaire physique", "4011",true),
							new Task("Stock-Audit de la section", "Stock-Audit de la section", "4012",true),
							new Task("Client-Preparation et suivi de la circularisation", "Client-Preparation et suivi de la circularisation", "4013",true),
							new Task("Client-Audit de la section", "Client-Audit de la section", "4014",true),
							new Task("Creances sur des entreprises liees", "Creances sur des entreprises liees", "4015",true),
							new Task("Autres creances", "Autres creances", "4016",true),
							new Task("Valeurs mobilieres", "Valeurs mobilieres", "4017",true),
							new Task("Avoirs en banques et encaisses", "Avoirs en banques et encaisses", "4018",true),
							new Task("Compte de regularisation actif", "Compte de regularisation actif", "4019",true),
							new Task("Capitaux propres", "Capitaux propres", "4020",true),
							new Task("Provisions pour risques et charges", "Provisions pour risques et charges", "4021",true),
							new Task("Dettes envers ets de credit", "Dettes envers ets de credit", "4022",true),
							new Task("Fournisseurs-preparation et suivi de la circularisation", "Fournisseurs-preparation et suivi de la circularisation", "4023",true),
							new Task("Fournisseurs-audit de la section", "Fournisseurs-audit de la section", "4024",true),
							new Task("Dettes sur des entreprises liees", "Dettes sur des entreprises liees", "4025",true),
							new Task("Autres dettes", "Autres dettes", "4026",true),
							new Task("Compte de regularisation passif", "Compte de regularisation passif", "4027",true),
							new Task("Compte de pertes et profits", "Compte de pertes et profits", "4028",true),
							new Task("Compte de pertes et profits-Resultats brut", "Compte de pertes et profits-Resultats brut", "4029",true),
							new Task("Compte de pertes et profits-Frais de personnel", "Compte de pertes et profits-Frais de personnel", "4030",true),
							new Task("Compte de pertes et profits-Autres charges déxploitation", "Compte de pertes et profits-Autres charges déxploitation", "4031",true),
							new Task("Compte de pertes et profits-Produits provenant de participations", "Compte de pertes et profits-Produits provenant de participations", "4032",true),
							new Task("Compte de pertes et profits-Resultat financier", "Compte de pertes et profits-Resultat financier", "4033",true),
							new Task("Compte de pertes et profits-Resultat exceptionel", "Compte de pertes et profits-Resultat exceptionel", "4034",true),
							new Task("Compte de pertes et profits-Impots", "Compte de pertes et profits-Impots", "4035",true),
							new Task("Engagements hors bilan", "Engagements hors bilan", "4036",true),
							new Task("Review", "Review", "4037",true),
							new Task("Copie de controle", "Copie de controle", "4038",true),
							new Task("Travail specifique ", "Travail specifique", "4039",true),
							new Task("Relation client ", "Relation client", "4040",true),
							
							
							new Task("Datev", "Datev", "4041",false),
							new Task("Organisation/Administration", "Planning, organisation travail, releves conges/TS, evaluation", "4042",false),
							new Task("Formation ", "IRE/KLUWER/KPMG Tax", "4043",false),
							new Task("Litterature/Formation interne ", "Litterature/Formation interne sur dossier", "4044",false),
							new Task("Divers", "Reunion, secretariat, verre, repas client, informatique, ctl Q", "4045",false),
							new Task("Conge legal", "Conge legal", "4046",false),
							new Task("Ferie", "Ferie", "4047",false),
							new Task("Formation dispense en interne a collegues", "Formation dispense en interne a collegues", "4048",false),
							new Task("Divers 2 ", "decompte client, facturation, offre niveau client IRL, suivi MPI", "4049",false),
							new Task("Maladie /medecin ", "Maladie /medecin", "4050",false)*/
				};
		
		
		for(int i =0; i<taskArray.length;i++){
			service.saveOne(taskArray[i]);
		}
		
		
		
		
		
		
	}

	// supression éléments des tables
	public void clean() {
		//On supprime tous les tasks
		for (Task task : service.getAll()) {
			service.deleteOne(task.getId());
		}
	}

	// affichage contenu table categorie
	private void dump() {
	
		 
		System.out.format("[tasks]%n");
		for (Task task : service.getAll()) {
			System.out.println(task);
		}
		
	}

	

	@Test()
	public void test01() {
		/*
		log("test01");
		dumpCategories();
		dumpArticles();
		// liste des catégories
		List<Categorie> categories = service.getAllCategories();
		assert 3 == categories.size();
		// liste des articles
		List<Article> articles = service.getAllArticles();
		assert 3 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test01")
	public void test02() {
		/*
		log("test02");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		// categorie B
		categories = service.getAllCategoriesWithNomLike("B%");
		assert 1 == categories.size();
		// categorie C
		categories = service.getAllCategoriesWithNomLike("C%");
		assert 1 == categories.size();
		// articles A
		List<Article> articles = service.getAllArticlesWithNomLike("A%");
		assert 2 == articles.size();
		// articles B
		articles = service.getAllArticlesWithNomLike("B%");
		assert 1 == articles.size();
		// articles C
		articles = service.getAllArticlesWithNomLike("C%");
		assert 0 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test02")
	public void test03() {
		/*
		log("test03");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// articles associés
		List<Article> articles = service.getArticlesFromCategorie(categorieA.getId());
		// vérification
		assert 2 == articles.size();
		// on ajoute un article
		Article articleA3 = new Article();
		articleA3.setNom("A3");
		// on le met dans la catégorie A
		articleA3.setCategorie(categorieA);
		// on le persiste
		service.saveArticle(articleA3);
		// articles associés à la catégorie A
		articles = service.getArticlesFromCategorie(categorieA.getId());
		// vérification - il doit y en avoir 1 de plus
		assert 3 == articles.size();
		// articles A
		articles = service.getAllArticlesWithNomLike("A%");
		assert 3 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test03")
	public void test04() {
		/*
		log("test04");
		// suppression catégorie B avec les articles associés
		List<Categorie> categories = service.getAllCategoriesWithNomLike("B%");
		assert 1 == categories.size();
		Categorie categorieB = categories.get(0);
		service.deleteCategoriesWithArticles(new Categorie[] { categorieB });
		// vérification
		categories = service.getAllCategoriesWithNomLike("B%");
		assert 0 == categories.size();
		// articles B
		List<Article> articles = service.getAllArticlesWithNomLike("B%");
		assert 0 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test04")
	public void test05() {
		/*
		log("test05");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// on modifie son nom
		categorieA.setNom("A+");
		service.updateCategorie(categorieA);
		// catégorie A
		categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		categorieA = categories.get(0);
		assert "A+".equals(categorieA.getNom());
		// article A2
		List<Article> articles = service.getAllArticlesWithNomLike("A2%");
		assert 1 == articles.size();
		Article articleA2 = articles.get(0);
		// on modifie son nom
		articleA2.setNom("A2+");
		service.updateArticle(articleA2);
		// article A2
		articles = service.getAllArticlesWithNomLike("A2%");
		assert 1 == articles.size();
		articleA2 = articles.get(0);
		assert "A2+".equals(articleA2.getNom());
		*/
	}

	@Test(dependsOnMethods = "test05")
	public void test06() {
		/*
		log("test06");
		// créer deux nouvelles catégories
		Categorie categorieD = new Categorie();
		categorieD.setNom("D");
		Categorie categorieE = new Categorie();
		categorieD.setNom("E");
		// créer 1 nouvel article avec un nom existant (violation contrainte d'unicité)
		Article articleD1 = new Article();
		articleD1.setNom("A1");
		// le relier à catégorie D
		articleD1.setCategorie(categorieD);
		categorieD.getArticles().add(articleD1);
		// on doit avoir une exception lors de la sauvegarde de article D1 et un rollback général
		boolean erreur = false;
		try {
			// persister les catégories et les articles associés
			service.saveCategoriesWithArticles(new Categorie[] { categorieD, categorieE });
		} catch (RuntimeException e) {
			erreur = true;
		}
		// vérifications : il y a du avoir une exception
		assert erreur;
		// et un rollback
		// catégorie D : elle ne doit pas exister
		List<Categorie> categories = service.getAllCategoriesWithNomLike("D%");
		assert 0 == categories.size();
		// catégorie E : elle ne doit pas exister
		categories = service.getAllCategoriesWithNomLike("E%");
		assert 0 == categories.size();
		// articles : il doit tjrs y avoir 3 articles
		List<Article> articles = service.getAllArticles();
		assert 3 == articles.size()
		*/
	}

	public ITaskService getService() {
		return service;
	}

	public void setService(ITaskService service) {
		this.service = service;
	}
}
